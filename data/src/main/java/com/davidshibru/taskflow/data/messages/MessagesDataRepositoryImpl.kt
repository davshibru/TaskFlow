package com.davidshibru.taskflow.data.messages

import com.davidshibru.taskflow.core.data.network.dto.ContentDto.Message
import com.davidshibru.taskflow.core.data.paging.PagingUtils
import com.davidshibru.taskflow.core.data.paging.firstEventOfType
import com.davidshibru.taskflow.data.MessagesDataRepository
import com.davidshibru.taskflow.data.messages.remote.MessagesApi
import com.davidshibru.taskflow.data.rooms.entities.RoomDataEntityId
import com.davidshibru.taskflow.data.rooms.fetchers.RoomBasicMessagesInfoFetcher
import com.davidshibru.taskflow.data.rooms.fetchers.RoomBasicMessagesInfoFetcher.RoomBasicMessagesInfo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class MessagesDataRepositoryImpl @Inject constructor(
    private val messagesApi: MessagesApi,
    private val pagingUtils: PagingUtils,
) : MessagesDataRepository, RoomBasicMessagesInfoFetcher {

    override suspend fun fetchBasicMessagesInfo(roomId: RoomDataEntityId): RoomBasicMessagesInfo {
        val lastMessage = pagingUtils.firstEventOfType<Message>{ pageToken ->
            messagesApi.getRoomMessages(roomId = roomId, from = pageToken)
        }?.body

        return RoomBasicMessagesInfoImpl(lastMessage)
    }

    private data class RoomBasicMessagesInfoImpl(
        override val lastMessage: String?,
        override val unreadMessageCound: Int = 0,
    ) : RoomBasicMessagesInfo

}