package com.davidshibru.taskflow.data.rooms

import com.davidshibru.taskflow.core.data.network.containerOf
import com.davidshibru.taskflow.core.essentials.collections.mapNotNullAsync
import com.davidshibru.taskflow.core.essentials.container.Container
import com.davidshibru.taskflow.core.essentials.container.LazyFlowSubject
import com.davidshibru.taskflow.core.essentials.container.SubjectFactory
import com.davidshibru.taskflow.core.essentials.container.containerMap
import com.davidshibru.taskflow.data.RoomsDataRepository
import com.davidshibru.taskflow.data.SessionProvider
import com.davidshibru.taskflow.data.getCurrentUserId
import com.davidshibru.taskflow.data.rooms.entities.RoomDataEntity
import com.davidshibru.taskflow.data.rooms.entities.RoomDataEntityId
import com.davidshibru.taskflow.data.rooms.exceptions.RoomNotFoundDataExceptions
import com.davidshibru.taskflow.data.rooms.fetchers.RoomFetcher
import com.davidshibru.taskflow.data.rooms.remote.RoomsApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class RoomsDataRepositoryImpl @Inject constructor(
    subjectFactory: SubjectFactory,
    private val roomsApi: RoomsApi,
    private val roomFetcher: RoomFetcher.Factory,
    private val sessionProvider: SessionProvider,
) : RoomsDataRepository {

    private val subject: LazyFlowSubject<List<RoomDataEntity>> = subjectFactory.create {
        delay(1000)
        val response = roomsApi.getJoinedRooms().unwrap()
        val roomFetcher = roomFetcher.create(sessionProvider.getCurrentUserId())
        val rooms = response.joinedRoomsIds.mapNotNullAsync { roomId ->
            roomFetcher.fetchRoom(roomId)
        }
        emit(rooms)
    }

    override fun getRooms(): Flow<Container<List<RoomDataEntity>>> {
        return subject.listenReloadable()
    }

    override suspend fun leaveRoom(
        roomId: RoomDataEntityId
    ): Container.Completed<Unit> = containerOf {
        delay(500)
        roomsApi.leaveRoom(roomId).unwrap()
        subject.updateIfSuccess { oldRooms ->
            oldRooms.filter { it.id != roomId }
        }
    }


    override fun getRoomById(roomId: RoomDataEntityId): Flow<Container<RoomDataEntity>> {
        return getRooms()
            .containerMap { rooms ->
                rooms.firstOrNull { it.id == roomId } ?: throw RoomNotFoundDataExceptions(roomId)
            }
    }
}
