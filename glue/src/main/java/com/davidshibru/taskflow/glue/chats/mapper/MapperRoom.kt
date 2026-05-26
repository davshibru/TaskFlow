package com.davidshibru.taskflow.glue.chats.mapper

import com.davidshibru.taskflow.core.essentials.entities.AbstractId
import com.davidshibru.taskflow.data.rooms.entities.RoomDataEntity
import com.davidshibru.taskflow.data.rooms.entities.RoomDataEntityId
import com.davidshibru.taskflow.feature.chats.domain.entities.Chat
import com.davidshibru.taskflow.feature.chats.domain.entities.ChatId
import javax.inject.Inject

interface MapperRoom {
    fun toChat(room: RoomDataEntity): Chat

    class Default @Inject constructor() : MapperRoom {
        override fun toChat(room: RoomDataEntity): Chat {
            return with(room) {
                Chat(
                    id = id.toChatId(),
                    title = title,
                    imageSource = imageSource,
                    lastMessage = lastMessage,
                    unreadMessageCount = unreadMessageCount
                )
            }
        }
    }

    class MapperId(
        value: String
    ): AbstractId(value), RoomDataEntityId, ChatId
}

internal fun ChatId.toRoomId(): RoomDataEntityId =
    (this as? MapperRoom.MapperId) ?: RoomDataEntityId(value)

internal fun RoomDataEntityId.toChatId(): ChatId =
    (this as? MapperRoom.MapperId) ?: ChatId(value)