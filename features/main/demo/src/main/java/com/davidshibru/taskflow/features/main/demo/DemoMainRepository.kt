package com.davidshibru.taskflow.features.main.demo

import com.davidshibru.taskflow.core.essentials.entities.Id
import com.davidshibru.taskflow.features.main.domain.entities.MainEntity
import com.davidshibru.taskflow.features.main.domain.repositories.MainRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DemoMainRepository @Inject constructor() : MainRepository {
    
    private val items = mutableListOf(
        MainEntity(
            id = Id(1),
            title = "Main item",
        )
    )

    override suspend fun getMainItems(): List<MainEntity> {
        return items.toList()
    }

    override suspend fun saveMainItem(entity: MainEntity) {
        val index = items.indexOfFirst { it.id == entity.id }
        if (index == -1) {
            items += entity
        } else {
            items[index] = entity
        }
    }
}