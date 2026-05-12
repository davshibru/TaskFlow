package com.davidshibru.taskflow.features.main.domain.usecases

import com.davidshibru.taskflow.features.main.domain.GetMainUseCase
import com.davidshibru.taskflow.features.main.domain.entities.MainEntity
import com.davidshibru.taskflow.features.main.domain.repositories.MainRepository
import javax.inject.Inject

internal class GetMainUseCaseImpl @Inject constructor(
    private val repository: MainRepository,
) : GetMainUseCase {

    override suspend operator fun invoke(): List<MainEntity> {
        return repository.getMainItems()
    }
}