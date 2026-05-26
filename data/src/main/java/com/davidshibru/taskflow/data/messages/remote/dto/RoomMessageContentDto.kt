package com.davidshibru.taskflow.data.messages.remote.dto

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