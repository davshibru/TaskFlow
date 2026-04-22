package com.davidshibru.taskflow.feature.chats.domain

import com.davidshibru.taskflow.core.essentials.container.Container
import com.davidshibru.taskflow.feature.chats.domain.entities.Chat
import kotlinx.coroutines.flow.Flow

interface GetChatsUseCase {
    operator fun invoke(): Flow<Container<List<Chat>>>
}