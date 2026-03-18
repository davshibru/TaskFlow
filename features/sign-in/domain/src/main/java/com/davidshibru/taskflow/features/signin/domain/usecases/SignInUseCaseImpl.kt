package com.davidshibru.taskflow.features.signin.domain.usecases

import com.davidshibru.taskflow.features.signin.domain.SignInUseCase
import com.davidshibru.taskflow.features.signin.domain.entities.Credentials
import com.davidshibru.taskflow.features.signin.domain.entities.validate
import com.davidshibru.taskflow.features.signin.domain.repositories.LocalTokenRepository
import com.davidshibru.taskflow.features.signin.domain.repositories.SignInRepository
import javax.inject.Inject

internal class SignInUseCaseImpl @Inject constructor(
    private val signInRepository: SignInRepository,
    private val localTokenRepository: LocalTokenRepository,
) : SignInUseCase {
    override suspend fun invoke(credentials: Credentials) {
        credentials.validate()
        val token = signInRepository.signIn(credentials)
        localTokenRepository.saveToken(token)
    }
}