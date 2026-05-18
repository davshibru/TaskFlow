package com.davidshibru.taskflow.features.profile.domain.repositories

import com.davidshibru.taskflow.features.profile.domain.entities.ProfileEntity

interface ProfileRepository {
    suspend fun getProfileItems(): List<ProfileEntity>
    suspend fun saveProfileItem(entity: ProfileEntity)
}