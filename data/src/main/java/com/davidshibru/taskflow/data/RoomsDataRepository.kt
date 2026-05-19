package com.davidshibru.taskflow.data

import com.davidshibru.taskflow.core.essentials.container.Container
import com.davidshibru.taskflow.data.rooms.entities.RoomDataEntity
import kotlinx.coroutines.flow.Flow

interface RoomsDataRepository {

    fun getRooms(): Flow<Container<List<RoomDataEntity>>>

}