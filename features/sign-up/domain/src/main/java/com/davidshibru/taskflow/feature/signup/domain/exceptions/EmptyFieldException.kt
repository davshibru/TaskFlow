package com.davidshibru.taskflow.feature.signup.domain.exceptions

import com.davidshibru.taskflow.feature.signup.domain.entities.InputField
import com.davidshibru.taskflow.feature.signup.domain.exceptions.base.AbstractValidationException
import com.davidshibru.taskflow.feature.signup.domain.resources.SignUpStringProvider

class EmptyFieldException(
    override val inputField: InputField.Text,
): AbstractValidationException(message = "$inputField is empty") {
    override fun getLocalizedErrorMessage(stringProvider: SignUpStringProvider): String {
        return stringProvider.emptyFieldError(inputField)
    }
}
