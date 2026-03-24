package com.davidshibru.taskflow.feature.signup.domain

import com.davidshibru.taskflow.feature.signup.domain.entities.NewAccount
import com.davidshibru.taskflow.feature.signup.domain.entities.ValidationResult

interface ValidateAccountUseCase {

    suspend operator fun invoke(account: NewAccount) : ValidationResult
}