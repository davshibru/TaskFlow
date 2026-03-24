package com.davidshibru.taskflow.feature.signup.domain.usecases

import com.davidshibru.taskflow.feature.signup.domain.ValidateAccountUseCase
import com.davidshibru.taskflow.feature.signup.domain.entities.NewAccount
import com.davidshibru.taskflow.feature.signup.domain.entities.ValidationResult
import com.davidshibru.taskflow.feature.signup.domain.validators.NewAccountValidator
import javax.inject.Inject

internal class ValidateAccountUseCaseImpl @Inject constructor(
    private val newAccountValidator: NewAccountValidator,
) : ValidateAccountUseCase {

    override suspend fun invoke(account: NewAccount): ValidationResult {
        return newAccountValidator.validate(account)
    }
}