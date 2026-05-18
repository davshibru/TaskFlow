package com.davidshibru.taskflow.features.profile.domain.usecases

import com.davidshibru.taskflow.features.profile.domain.GetProfileUseCase
import com.davidshibru.taskflow.features.profile.domain.entities.ProfileEntity
import com.davidshibru.taskflow.features.profile.domain.repositories.ProfileRepository
import javax.inject.Inject

internal class GetProfileUseCaseImpl @Inject constructor(
    private val repository: ProfileRepository,
) : GetProfileUseCase {

    override suspend operator fun invoke(): List<ProfileEntity> {
        return repository.getProfileItems()
    }
}