package com.davidshibru.taskflow.data.rooms.remote.dto

import com.davidshibru.taskflow.core.data.network.dto.EventType
import kotlinx.serialization.Serializable

@Serializable
data class RoomMessageContentDto(
    val body: String,
) {

    companion object {
        val Type = EventType("m.room.message", RoomMessageContentDto::class)
    }
}
