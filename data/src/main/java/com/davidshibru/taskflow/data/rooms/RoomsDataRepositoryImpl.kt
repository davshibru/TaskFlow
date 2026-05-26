package com.davidshibru.taskflow.data.rooms

import com.davidshibru.taskflow.core.essentials.container.Container
import com.davidshibru.taskflow.core.essentials.container.LazyFlowSubject
import com.davidshibru.taskflow.core.essentials.container.SubjectFactory
import com.davidshibru.taskflow.data.RoomsDataRepository
import com.davidshibru.taskflow.data.SessionProvider
import com.davidshibru.taskflow.data.getCurrentUserId
import com.davidshibru.taskflow.data.rooms.entities.RoomDataEntity
import com.davidshibru.taskflow.data.rooms.remote.RoomsApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

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
        val rooms = response.joinedRoomsIds.mapNotNull { roomId ->
            roomFetcher.fetchRoom(roomId)
        }
        emit(rooms)
    }

    override fun getRooms(): Flow<Container<List<RoomDataEntity>>> {
        return subject.listenReloadable()
    }
}
