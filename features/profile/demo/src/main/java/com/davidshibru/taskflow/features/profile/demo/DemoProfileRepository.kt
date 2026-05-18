package com.davidshibru.taskflow.features.profile.demo

import com.davidshibru.taskflow.core.essentials.entities.Id
import com.davidshibru.taskflow.features.profile.domain.entities.ProfileEntity
import com.davidshibru.taskflow.features.profile.domain.repositories.ProfileRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DemoProfileRepository @Inject constructor() : ProfileRepository {
    
    private val items = mutableListOf(
        ProfileEntity(
            id = Id(1),
            title = "Profile item",
        )
    )

    override suspend fun getProfileItems(): List<ProfileEntity> {
        return items.toList()
    }

    override suspend fun saveProfileItem(entity: ProfileEntity) {
        val index = items.indexOfFirst { it.id == entity.id }
        if (index == -1) {
            items += entity
        } else {
            items[index] = entity
        }
    }
}