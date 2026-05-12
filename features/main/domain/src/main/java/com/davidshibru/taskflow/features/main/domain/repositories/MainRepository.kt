package com.davidshibru.taskflow.features.main.domain.repositories

import com.davidshibru.taskflow.features.main.domain.entities.MainEntity

interface MainRepository {
    suspend fun getMainItems(): List<MainEntity>
    suspend fun saveMainItem(entity: MainEntity)
}