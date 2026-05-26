package com.davidshibru.taskflow.feature.chats.domain.repositories

import com.davidshibru.taskflow.core.essentials.container.Container
import com.davidshibru.taskflow.feature.chats.domain.entities.Chat
import com.davidshibru.taskflow.feature.chats.domain.entities.ChatId
import kotlinx.coroutines.flow.Flow

interface ChatsRepository {
    fun getChats(): Flow<Container<List<Chat>>>
    suspend fun deleteChat(chatId: ChatId)
    suspend fun getChatById(chatId: ChatId): Chat
}