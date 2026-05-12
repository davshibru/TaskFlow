package com.davidshibru.taskflow.features.main.domain

import com.davidshibru.taskflow.features.main.domain.entities.MainEntity

interface GetMainUseCase {
    suspend operator fun invoke(): List<MainEntity>
}