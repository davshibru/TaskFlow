package com.davidshibru.taskflow.data

import com.davidshibru.taskflow.core.essentials.container.Container
import com.davidshibru.taskflow.core.essentials.container.Container.Completed
import com.davidshibru.taskflow.data.rooms.entities.RoomDataEntity
import com.davidshibru.taskflow.data.rooms.entities.RoomDataEntityId
import kotlinx.coroutines.flow.Flow

interface RoomsDataRepository {

    fun getRooms(): Flow<Container<List<RoomDataEntity>>>

    suspend fun leaveRoom(roomId: RoomDataEntityId): Completed<Unit>

    fun getRoomById(roomId: RoomDataEntityId): Flow<Container<RoomDataEntity?>>
}