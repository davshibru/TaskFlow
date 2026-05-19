package com.davidshibru.taskflow.data.rooms

import com.davidshibru.taskflow.core.essentials.container.Container
import com.davidshibru.taskflow.core.essentials.container.Container.Loading.successContainer
import com.davidshibru.taskflow.core.essentials.entities.Id
import com.davidshibru.taskflow.data.RoomsDataRepository
import com.davidshibru.taskflow.data.rooms.entities.RoomDataEntity
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.collections.listOf

internal class RoomsDataRepositoryImpl @Inject constructor(

) : RoomsDataRepository {
    override fun getRooms(): Flow<Container<List<RoomDataEntity>>> {
        return flow {
            delay(1000)
            val result = successContainer(
                listOf(
                    RoomDataEntity(
                        Id(1),
                        "Title",
                        "Message"
                    )
                )
            )

            emit(result)
        }
    }
}