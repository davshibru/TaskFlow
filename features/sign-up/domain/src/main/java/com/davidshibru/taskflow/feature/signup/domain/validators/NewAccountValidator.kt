package com.davidshibru.taskflow.feature.signup.domain.validators

import com.davidshibru.taskflow.feature.signup.domain.entities.NewAccount
import com.davidshibru.taskflow.feature.signup.domain.entities.ValidationResult
import com.davidshibru.taskflow.feature.signup.domain.entities.ValidationResult.Companion.combine
import com.davidshibru.taskflow.feature.signup.domain.entities.toFieldValues
import com.davidshibru.taskflow.feature.signup.domain.exceptions.LoginAlreadyExistsException
import com.davidshibru.taskflow.feature.signup.domain.exceptions.PasswordMismatchException
import com.davidshibru.taskflow.feature.signup.domain.repositories.LoginAvailabilityRepository
import javax.inject.Inject

internal interface NewAccountValidator {
    suspend fun validate(account: NewAccount): ValidationResult
}

internal class NewAccountValidatorImpl @Inject constructor(
    private val loginAvailabilityRepository: LoginAvailabilityRepository,
) : NewAccountValidator {

    override suspend fun validate(account: NewAccount): ValidationResult {
        val fieldValues = account.toFieldValues()

        var validationResults: ValidationResult = fieldValues
            .map { it.validate() }
            .combine()

        if (account.password != account.repeatPassword) {
            validationResults += PasswordMismatchException()
        }

        if (!loginAvailabilityRepository.isLoginAvailable(account.login)) {
            validationResults += LoginAlreadyExistsException(account.login)
        }

        return validationResults
    }

}