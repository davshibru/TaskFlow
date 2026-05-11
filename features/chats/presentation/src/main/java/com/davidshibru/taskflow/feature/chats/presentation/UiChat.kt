package com.davidshibru.taskflow.feature.chats.presentation

import com.davidshibru.taskflow.core.essentials.entities.Id
import com.davidshibru.taskflow.core.essentials.entities.ImageSource
import com.davidshibru.taskflow.feature.chats.domain.entities.Chat

data class UiChat(
    val id: Id,
    val title: String,
    val imageSource: ImageSource = ImageSource.Empty,
    val lastMessage: String? = null,
    val unreadMessageCount: Int = 0,
    val isEnabled: Boolean,
)



val UiChat.hasUnreadMessages: Boolean get() = unreadMessageCount > 0