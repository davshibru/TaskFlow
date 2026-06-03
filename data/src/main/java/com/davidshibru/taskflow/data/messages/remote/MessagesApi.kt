package com.davidshibru.taskflow.data.messages.remote

import com.davidshibru.taskflow.core.data.network.dto.ChunkDto
import com.davidshibru.taskflow.core.data.network.dto.ContentDto
import com.davidshibru.taskflow.core.data.network.dto.DirectionQueryParam
import com.davidshibru.taskflow.core.data.network.dto.EventDto
import com.davidshibru.taskflow.core.data.network.dto.PageTokenQueryParam
import com.davidshibru.taskflow.core.essentials.container.Container.Completed
import com.davidshibru.taskflow.data.rooms.entities.RoomDataEntityId
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface MessagesApi {

    @GET("rooms/{roomId}/messages")
    suspend fun getRoomMessages(
        @Path("roomId") roomId: RoomDataEntityId,
        @Query("dir") direction: DirectionQueryParam = DirectionQueryParam.Backward,
        @Query("from") from: PageTokenQueryParam? = null,
    ): Completed<ChunkDto<EventDto<ContentDto>>>
}