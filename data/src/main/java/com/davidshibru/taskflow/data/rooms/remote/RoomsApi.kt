package com.davidshibru.taskflow.data.rooms.remote

import com.davidshibru.taskflow.core.data.network.dto.ChunkDto
import com.davidshibru.taskflow.core.data.network.dto.ContentDto
import com.davidshibru.taskflow.core.data.network.dto.EventDto
import com.davidshibru.taskflow.core.essentials.container.Container.Completed
import com.davidshibru.taskflow.data.rooms.entities.RoomDataEntityId
import com.davidshibru.taskflow.data.rooms.remote.dto.JoinedRoomIdsResponseDto
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

internal interface RoomsApi {

    @GET("joined_rooms")
    suspend fun getJoinedRooms(): Completed<JoinedRoomIdsResponseDto>

    @GET("rooms/{roomId}/members")
    suspend fun getRoomMembers(
        @Path("roomId") roomId: RoomDataEntityId,
    ): Completed<ChunkDto<EventDto<ContentDto.Member>>>

    @POST("rooms/{roomId}/leave")
    suspend fun leaveRoom(
        @Path("roomId") roomId: RoomDataEntityId,
    ): Completed<Unit>
}