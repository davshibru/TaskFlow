package com.davidshibru.taskflow.feature.chats.domain.entities

import com.davidshibru.taskflow.core.essentials.entities.AbstractId
import com.davidshibru.taskflow.core.essentials.entities.Id
import com.davidshibru.taskflow.core.essentials.entities.ImageSource

data class Chat(
    val id: ChatId,
    val title: String,
    val imageSource: ImageSource = ImageSource.Empty,
    val lastMessage: String? = null,
    val unreadMessageCount: Int = 0,
)

val Chat.hasUnreadMessages: Boolean get() = unreadMessageCount > 0

interface ChatId: Id {
    private class Default(value: String): AbstractId(value), ChatId
    companion object{
        operator fun invoke(value: String): ChatId = Default(value)
    }
}