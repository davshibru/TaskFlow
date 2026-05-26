package com.davidshibru.taskflow.feature.chats.domain.resources

import com.davidshibru.taskflow.core.essentials.resources.StringProvider
import com.davidshibru.taskflow.feature.chats.domain.entities.Chat
import com.davidshibru.taskflow.feature.chats.domain.entities.ChatId

interface ChatsStringProvider : StringProvider {
    val confirmDeleteDialogTitle: String
    fun confirmDeleteDialogMessage(chat: Chat): String
    fun chatNotFoundErrorMessage(chatId: ChatId): String
}