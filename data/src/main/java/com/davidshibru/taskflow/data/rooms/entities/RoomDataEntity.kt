package com.davidshibru.taskflow.data.rooms.entities

import com.davidshibru.taskflow.core.essentials.entities.Id
import com.davidshibru.taskflow.core.essentials.entities.ImageSource

data class RoomDataEntity(
    val id: Id,
    val title: String,
    val lastMessage: String?,
    val unreadMessageCount: Int = 0,
    val imageSource: ImageSource = ImageSource.Empty,
)