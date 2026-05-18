package com.davidshibru.taskflow.features.profile.domain.usecases

import com.davidshibru.taskflow.features.profile.domain.SaveProfileUseCase
import com.davidshibru.taskflow.features.profile.domain.entities.ProfileEntity
import com.davidshibru.taskflow.features.profile.domain.repositories.ProfileRepository
import javax.inject.Inject

internal class SaveProfileUseCaseImpl @Inject constructor(
    private val repository: ProfileRepository,
) : SaveProfileUseCase {

    override suspend operator fun invoke(entity: ProfileEntity) {
        repository.saveProfileItem(entity)
    }
}