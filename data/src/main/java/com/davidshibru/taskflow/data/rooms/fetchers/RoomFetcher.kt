package com.davidshibru.taskflow.data.rooms.fetchers

import com.davidshibru.taskflow.core.essentials.entities.UserId
import com.davidshibru.taskflow.data.rooms.entities.RoomDataEntity
import com.davidshibru.taskflow.data.rooms.entities.RoomDataEntityId
import com.davidshibru.taskflow.data.rooms.remote.RoomsApi
import javax.inject.Inject

internal interface RoomFetcher {

    class Params(
        val currentUserId: UserId,
    )

    interface Factory {
        fun create(params: Params): RoomFetcher
        fun create(currentUserId: UserId) = create(Params(currentUserId))
    }

    suspend fun fetchRoom(
        roomId: RoomDataEntityId,
    ): RoomDataEntity?
}

internal class RoomFetcherImpl constructor(
    params: RoomFetcher.Params,
    dependencies: Dependencies,
) : RoomFetcher {

    private val currentUserId = params.currentUserId
    private val roomsApi: RoomsApi = dependencies.roomsApi
    private val roomBasicMessagesInfoFetcher = dependencies.roomBasicMessagesInfoFetcher

    override suspend fun fetchRoom(
        roomId: RoomDataEntityId
    ): RoomDataEntity? {
        val title = fetchRoomTitle(currentUserId, roomId) ?: return null
        val messagesBasicInfo = roomBasicMessagesInfoFetcher.fetchBasicMessagesInfo(roomId)
        return RoomDataEntity(roomId, title, messagesBasicInfo.lastMessage)
    }

    private suspend fun fetchRoomTitle(
        currentUserId: UserId,
        roomId: RoomDataEntityId,
    ): String? {
        val roomMembersResponse = roomsApi.getRoomMembers(roomId).unwrap()
        return roomMembersResponse.data
            .firstOrNull { event ->
                event.userId != currentUserId
            }
            ?.content
            ?.displayname
    }

    class Dependencies @Inject constructor(
        val roomsApi: RoomsApi,
        val roomBasicMessagesInfoFetcher: RoomBasicMessagesInfoFetcher,
    )

    class FactoryImpl @Inject constructor(
        private val dependencies: Dependencies,
    ) : RoomFetcher.Factory {
        override fun create(params: RoomFetcher.Params): RoomFetcher {
            return RoomFetcherImpl(params, dependencies)
        }
    }
}