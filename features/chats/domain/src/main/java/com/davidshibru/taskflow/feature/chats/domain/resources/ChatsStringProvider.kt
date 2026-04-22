package com.davidshibru.taskflow.feature.chats.domain.resources

import com.davidshibru.taskflow.core.essentials.resources.StringProvider
import com.davidshibru.taskflow.feature.chats.domain.entities.Chat

interface ChatsStringProvider : StringProvider {
    val confirmDeleteDialogTitle: String
    fun confirmDeleteDialogMessage(chat: Chat): String
}