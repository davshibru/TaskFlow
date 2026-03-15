package com.davidshibru.taskflow.feature.init.domain.usecases

import com.davidshibru.taskflow.feature.init.domain.IsAuthorizedUseCase
import com.davidshibru.taskflow.feature.init.domain.repositories.AuthRepository
import javax.inject.Inject

internal class IsAuthorizedUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
) : IsAuthorizedUseCase {

    override suspend fun invoke(): Boolean {
        return authRepository.isAuthorized()
    }

}