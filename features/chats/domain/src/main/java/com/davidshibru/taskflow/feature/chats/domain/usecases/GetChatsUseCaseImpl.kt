package com.davidshibru.taskflow.feature.chats.domain.usecases

import com.davidshibru.taskflow.core.essentials.container.Container
import com.davidshibru.taskflow.feature.chats.domain.GetChatsUseCase
import com.davidshibru.taskflow.feature.chats.domain.entities.Chat
import com.davidshibru.taskflow.feature.chats.domain.repositories.ChatsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class GetChatsUseCaseImpl @Inject constructor(
    private val chatsRepository: ChatsRepository,
) : GetChatsUseCase {

    override operator fun invoke(): Flow<Container<List<Chat>>> {
        return chatsRepository.getChats()
    }
}