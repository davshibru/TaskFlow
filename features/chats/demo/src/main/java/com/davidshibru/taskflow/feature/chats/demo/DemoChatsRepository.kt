package com.davidshibru.taskflow.feature.chats.demo

import com.davidshibru.taskflow.core.essentials.container.Container
import com.davidshibru.taskflow.core.essentials.entities.Id
import com.davidshibru.taskflow.feature.chats.domain.entities.Chat
import com.davidshibru.taskflow.feature.chats.domain.repositories.ChatsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DemoChatsRepository @Inject constructor() : ChatsRepository {

    private var chats = listOf(
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
    )

    private val subject = flow {
        delay(1000)
        emit(Container.success(chats))
    }

    override fun getChats(): Flow<Container<List<Chat>>> {
        return subject
    }

    override suspend fun deleteChat(chatId: Id) {
        chats = chats.filter { it.id != chatId }
    }

    override suspend fun getChatById(chatId: Id): Chat {
        return chats.first { it.id == chatId }
    }
}
