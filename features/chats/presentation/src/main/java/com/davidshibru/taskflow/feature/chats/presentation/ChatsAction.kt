package com.davidshibru.taskflow.feature.chats.presentation

import com.davidshibru.taskflow.core.essentials.entities.Id

sealed class ChatsAction {

    data class DeleteChat(val chatId: Id): ChatsAction()
}