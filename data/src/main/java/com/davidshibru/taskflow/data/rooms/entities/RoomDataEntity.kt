package com.davidshibru.taskflow.data.rooms.entities

import com.davidshibru.taskflow.core.essentials.entities.AbstractId
import com.davidshibru.taskflow.core.essentials.entities.Id
import com.davidshibru.taskflow.core.essentials.entities.ImageSource

data class RoomDataEntity(
    val id: RoomDataEntityId,
    val title: String,
    val lastMessage: String?,
    val unreadMessageCount: Int = 0,
    val imageSource: ImageSource = ImageSource.Empty,
)

interface RoomDataEntityId: Id {
    private class Default(value: String): AbstractId(value), RoomDataEntityId
    companion object {
        operator fun invoke(value: String): RoomDataEntityId = Default(value)
    }
}