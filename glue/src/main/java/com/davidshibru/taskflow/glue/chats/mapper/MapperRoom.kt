package com.davidshibru.taskflow.glue.chats.mapper

import com.davidshibru.taskflow.data.rooms.entities.RoomDataEntity
import com.davidshibru.taskflow.feature.chats.domain.entities.Chat
import javax.inject.Inject

interface MapperRoom {
    fun toChat(room: RoomDataEntity): Chat

    class Default @Inject constructor() : MapperRoom {
        override fun toChat(room: RoomDataEntity): Chat {
            return with(room) {
                Chat(
                    id = id,
                    title = title,
                    imageSource = imageSource,
                    lastMessage = lastMessage,
                    unreadMessageCount = unreadMessageCount
                )
            }
        }
    }
}