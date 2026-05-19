package com.davidshibru.taskflow.data.rooms

import com.davidshibru.taskflow.core.data.network.containerOf
import com.davidshibru.taskflow.core.essentials.container.Container
import com.davidshibru.taskflow.data.RoomsDataRepository
import com.davidshibru.taskflow.data.SessionProvider
import com.davidshibru.taskflow.data.getCurrentUserId
import com.davidshibru.taskflow.data.rooms.entities.RoomDataEntity
import com.davidshibru.taskflow.data.rooms.remote.RoomsApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class RoomsDataRepositoryImpl @Inject constructor(
    private val roomsApi: RoomsApi,
    private val sessionProvider: SessionProvider,
) : RoomsDataRepository {
    override fun getRooms(): Flow<Container<List<RoomDataEntity>>> {
        return flow {
            delay(1000)

            emit(loadRooms())
        }
    }

    suspend fun loadRooms() = containerOf {
        val response = roomsApi.getJoinedRooms().unwrap()
        val currentUserId = sessionProvider.getCurrentUserId()
        response.joinedRoomsIds.mapNotNull { roomsId ->

            val roomMembersResponse = roomsApi.getRoomMembers(roomsId).unwrap()
            val title = roomMembersResponse.data.firstOrNull { event ->
                event.userId != currentUserId
            }
                ?.content
                ?.displayname
                ?: return@mapNotNull null

            RoomDataEntity(
                roomsId, title, "Message"
            )
        }
    }
}