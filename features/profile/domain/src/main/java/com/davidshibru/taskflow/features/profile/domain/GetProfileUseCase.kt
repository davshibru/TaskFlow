package com.davidshibru.taskflow.features.profile.domain

import com.davidshibru.taskflow.features.profile.domain.entities.ProfileEntity

interface GetProfileUseCase {
    suspend operator fun invoke(): List<ProfileEntity>
}