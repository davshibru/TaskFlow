package com.davidshibru.taskflow.data.rooms.remote.dto

import com.davidshibru.taskflow.data.rooms.entities.RoomDataEntityId
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class JoinedRoomIdsResponseDto(
    private val joinedRooms: List<String>,
) {
    @Transient
    val joinedRoomsIds: List<RoomDataEntityId> = joinedRooms.map { RoomDataEntityId(it) }
}
