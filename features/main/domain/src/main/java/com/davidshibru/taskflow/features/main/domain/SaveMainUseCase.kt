package com.davidshibru.taskflow.features.main.domain

import com.davidshibru.taskflow.features.main.domain.entities.MainEntity

interface SaveMainUseCase {
    suspend operator fun invoke(entity: MainEntity)
}