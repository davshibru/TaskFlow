package com.davidshibru.taskflow.feature.chats.domain.entities

import com.davidshibru.taskflow.core.essentials.entities.Id
import com.davidshibru.taskflow.core.essentials.entities.ImageSource

data class Chat(
    val id: Id,
    val title: String,
    val imageSource: ImageSource = ImageSource.Empty,
    val lastMessage: String? = null,
    val unreadMessageCount: Int = 0,
)

val Chat.hasUnreadMessages: Boolean get() = unreadMessageCount > 0