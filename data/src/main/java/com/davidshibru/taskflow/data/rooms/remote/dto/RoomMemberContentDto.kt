package com.davidshibru.taskflow.data.rooms.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class RoomMemberContentDto(
    val displayname: String,
)