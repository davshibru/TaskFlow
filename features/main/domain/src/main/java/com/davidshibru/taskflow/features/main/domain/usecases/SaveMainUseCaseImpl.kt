package com.davidshibru.taskflow.features.main.domain.usecases

import com.davidshibru.taskflow.features.main.domain.SaveMainUseCase
import com.davidshibru.taskflow.features.main.domain.entities.MainEntity
import com.davidshibru.taskflow.features.main.domain.repositories.MainRepository
import javax.inject.Inject

internal class SaveMainUseCaseImpl @Inject constructor(
    private val repository: MainRepository,
) : SaveMainUseCase {

    override suspend operator fun invoke(entity: MainEntity) {
        repository.saveMainItem(entity)
    }
}