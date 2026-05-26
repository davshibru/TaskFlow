package com.davidshibru.taskflow.glue.chats

import com.davidshibru.taskflow.core.essentials.container.Container
import com.davidshibru.taskflow.core.essentials.container.containerMap
import com.davidshibru.taskflow.core.essentials.container.map
import com.davidshibru.taskflow.core.essentials.container.mapException
import com.davidshibru.taskflow.data.RoomsDataRepository
import com.davidshibru.taskflow.data.rooms.entities.RoomDataEntity
import com.davidshibru.taskflow.data.rooms.exceptions.RoomNotFoundDataExceptions
import com.davidshibru.taskflow.feature.chats.domain.entities.Chat
import com.davidshibru.taskflow.feature.chats.domain.entities.ChatId
import com.davidshibru.taskflow.feature.chats.domain.exceptions.ChatNotFoundException
import com.davidshibru.taskflow.feature.chats.domain.repositories.ChatsRepository
import com.davidshibru.taskflow.glue.chats.mapper.MapperRoom
import com.davidshibru.taskflow.glue.chats.mapper.toChatId
import com.davidshibru.taskflow.glue.chats.mapper.toRoomId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.first
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

    override suspend fun deleteChat(chatId: ChatId) {
        roomsDataRepository.leaveRoom(chatId.toRoomId()).unwrap()
    }

    override suspend fun getChatById(chatId: ChatId): Chat {
        return roomsDataRepository
            .getRoomById(chatId.toRoomId())
            .filterIsInstance<Container.Completed<RoomDataEntity>>()
            .first()
            .map { room -> mapperRoom.toChat(room) }
            .mapException(RoomNotFoundDataExceptions::class) { originalException ->
                ChatNotFoundException(
                    chatId = originalException.roomId.toChatId(),
                    cause = originalException,
                )
            }
            .unwrap()
    }
}