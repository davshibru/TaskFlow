package com.davidshibru.taskflow.feature.chats.demo

import com.davidshibru.taskflow.core.essentials.container.Container
import com.davidshibru.taskflow.core.essentials.entities.Id
import com.davidshibru.taskflow.feature.chats.domain.entities.Chat
import com.davidshibru.taskflow.feature.chats.domain.repositories.ChatsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DemoChatsRepository @Inject constructor() : ChatsRepository {

    private val chats = MutableStateFlow(listOf(
        Chat(
            id = Id(1),
            title = "John",
            lastMessage = "Hello!",
            unreadMessageCount = 2,
        ),
        Chat(
            id = Id(2),
            title = "Jane",
            lastMessage = "Lorem Ipsim Test Test!",
            unreadMessageCount = 1,
        ),
        Chat(
            id = Id(3),
            title = "Gendalph White",
            lastMessage = null,
            unreadMessageCount = 0,
        ),
    ))

    override fun getChats(): Flow<Container<List<Chat>>> {
        return chats
            .onStart { delay(1000) }
            .map { Container.success(it) }
    }

    override suspend fun deleteChat(chatId: Id) {
        chats.update { currentChats ->
            currentChats.filter { it.id != chatId }
        }
    }

    override suspend fun getChatById(chatId: Id): Chat {
        return chats.value.first { it.id == chatId }
    }
}
