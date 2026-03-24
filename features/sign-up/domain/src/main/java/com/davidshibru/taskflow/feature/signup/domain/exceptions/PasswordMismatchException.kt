package com.davidshibru.taskflow.feature.signup.domain.exceptions

import com.davidshibru.taskflow.feature.signup.domain.entities.InputField
import com.davidshibru.taskflow.feature.signup.domain.exceptions.base.AbstractValidationException
import com.davidshibru.taskflow.feature.signup.domain.resources.SignUpStringProvider

class PasswordMismatchException(): AbstractValidationException(message = "Passwords don't match") {

    override val inputField = InputField.RepeatPassword

    override fun getLocalizedErrorMessage(stringProvider: SignUpStringProvider): String {
        return stringProvider.passwordMismatchError
    }
}
