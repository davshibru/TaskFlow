package com.davidshibru.taskflow.feature.chats.presentation

import com.davidshibru.taskflow.feature.chats.domain.entities.ChatId

sealed class ChatsAction {

    data class DeleteChat(val chatId: ChatId): ChatsAction()
}