package com.davidshibru.taskflow.data.rooms.exceptions

import com.davidshibru.taskflow.core.essentials.exception.base.AbstractAppException
import com.davidshibru.taskflow.data.rooms.entities.RoomDataEntityId

class RoomNotFoundDataExceptions(
    val roomId: RoomDataEntityId,
): AbstractAppException(
    message = "Room with id=$roomId has been not found."
)