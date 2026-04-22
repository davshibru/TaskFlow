package com.davidshibru.taskflow.feature.chats.domain.repositories

import com.davidshibru.taskflow.core.essentials.container.Container
import com.davidshibru.taskflow.core.essentials.entities.Id
import com.davidshibru.taskflow.feature.chats.domain.entities.Chat
import kotlinx.coroutines.flow.Flow

interface ChatsRepository {
    fun getChats(): Flow<Container<List<Chat>>>
    suspend fun deleteChat(chatId: Id)
    suspend fun getChatById(chatId: Id): Chat
}