package com.davidshibru.taskflow.glue.chats

import com.davidshibru.taskflow.core.essentials.container.Container
import com.davidshibru.taskflow.core.essentials.container.containerMap
import com.davidshibru.taskflow.core.essentials.entities.Id
import com.davidshibru.taskflow.data.RoomsDataRepository
import com.davidshibru.taskflow.feature.chats.domain.entities.Chat
import com.davidshibru.taskflow.feature.chats.domain.repositories.ChatsRepository
import com.davidshibru.taskflow.glue.chats.mapper.MapperRoom
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class ChatsRepositoryImpl @Inject constructor(
    private val mapperRoom: MapperRoom,
    private val roomsDataRepository: RoomsDataRepository,
) : ChatsRepository {
    override fun getChats(): Flow<Container<List<Chat>>> {
        return roomsDataRepository.getRooms()
            .containerMap { list ->
                list.map { mapperRoom.toChat(it) }
            }
    }

    override suspend fun deleteChat(chatId: Id) {
        TODO("Not yet implemented")
    }

    override suspend fun getChatById(chatId: Id): Chat {
        TODO("Not yet implemented")
    }
}