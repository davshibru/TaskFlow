package com.davidshibru.taskflow.feature.init.domain

interface IsAuthorizedUseCase  {
    suspend operator fun invoke(): Boolean
}