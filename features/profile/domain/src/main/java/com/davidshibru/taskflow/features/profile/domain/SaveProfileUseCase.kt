package com.davidshibru.taskflow.features.profile.domain

import com.davidshibru.taskflow.features.profile.domain.entities.ProfileEntity

interface SaveProfileUseCase {
    suspend operator fun invoke(entity: ProfileEntity)
}