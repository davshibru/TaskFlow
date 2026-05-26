package com.davidshibru.taskflow.data.rooms.fetchers

import com.davidshibru.taskflow.data.rooms.entities.RoomDataEntityId

internal interface RoomBasicMessagesInfoFetcher {

    suspend fun fetchBasicMessagesInfo(roomId: RoomDataEntityId): RoomBasicMessagesInfo

    interface RoomBasicMessagesInfo {
        val lastMessage: String?
        val unreadMessageCound: Int
    }
}