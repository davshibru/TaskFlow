package com.davidshibru.taskflow.feature.chats.domain.usecases

import com.davidshibru.taskflow.feature.chats.domain.DeleteChatUseCase
import com.davidshibru.taskflow.feature.chats.domain.effects.ChatsUserChoices
import com.davidshibru.taskflow.feature.chats.domain.entities.ChatId
import com.davidshibru.taskflow.feature.chats.domain.repositories.ChatsRepository
import javax.inject.Inject

internal class DeleteChatUseCaseImpl @Inject constructor(
    private val chatsRepository: ChatsRepository,
    private val userChoices: ChatsUserChoices,
): DeleteChatUseCase {

    override suspend operator fun invoke(chatId: ChatId) {
        val chat = chatsRepository.getChatById(chatId)
        val isConfirmed = userChoices.confirmChatRemoval(chat)
        if (isConfirmed) {
            chatsRepository.deleteChat(chatId)
        }
    }

}