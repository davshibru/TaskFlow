package com.davidshibru.taskflow.feature.chats.domain

import com.davidshibru.taskflow.feature.chats.domain.entities.ChatId

interface DeleteChatUseCase {

    suspend operator fun invoke(chatId: ChatId)

}