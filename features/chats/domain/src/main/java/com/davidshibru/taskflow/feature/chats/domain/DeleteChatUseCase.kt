package com.davidshibru.taskflow.feature.chats.domain

import com.davidshibru.taskflow.core.essentials.entities.Id

interface DeleteChatUseCase {

    suspend operator fun invoke(chatId: Id)

}