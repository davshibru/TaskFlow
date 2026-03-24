package com.davidshibru.taskflow.feature.signup.domain.usecases

import com.davidshibru.taskflow.feature.signup.domain.SignUpUseCase
import com.davidshibru.taskflow.feature.signup.domain.entities.NewAccount
import com.davidshibru.taskflow.feature.signup.domain.entities.ValidationResult
import com.davidshibru.taskflow.feature.signup.domain.repositories.CreateAccountRepository
import com.davidshibru.taskflow.feature.signup.domain.validators.NewAccountValidator
import javax.inject.Inject

internal class SignUpUseCaseImpl @Inject constructor(
    private val newAccountValidator: NewAccountValidator,
    private val createAccountRepository: CreateAccountRepository,
) : SignUpUseCase{
    override suspend fun invoke(account: NewAccount) {
        val validationResult = newAccountValidator.validate(account)
        when (validationResult) {
            is ValidationResult.Failure -> throw validationResult.exceptions.first()
            ValidationResult.Success -> createAccountRepository.createAccount(account)
        }
    }
}