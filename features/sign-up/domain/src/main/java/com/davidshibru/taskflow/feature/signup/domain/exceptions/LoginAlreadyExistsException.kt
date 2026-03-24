package com.davidshibru.taskflow.feature.signup.domain.exceptions

import com.davidshibru.taskflow.feature.signup.domain.entities.InputField
import com.davidshibru.taskflow.feature.signup.domain.exceptions.base.AbstractValidationException
import com.davidshibru.taskflow.feature.signup.domain.resources.SignUpStringProvider

class LoginAlreadyExistsException(
    login: String,
) : AbstractValidationException(message = "Login \"$login\" already exists") {

    override val inputField = InputField.Login

    override fun getLocalizedErrorMessage(stringProvider: SignUpStringProvider): String {
        return stringProvider.loginAlreadyExistsError
    }
}
