package com.davidshibru.taskflow.feature.signup.domain.exceptions

import com.davidshibru.taskflow.feature.signup.domain.entities.InputField
import com.davidshibru.taskflow.feature.signup.domain.exceptions.base.AbstractValidationException
import com.davidshibru.taskflow.feature.signup.domain.resources.SignUpStringProvider

class TooShortValueException(
    override val inputField: InputField.Text,
    val value: String,
): AbstractValidationException(message = "$inputField is too short (min required chars = ${inputField.minChars}, current length = ${value.length}") {
    override fun getLocalizedErrorMessage(stringProvider: SignUpStringProvider): String {
        return stringProvider.tooShortValueError(inputField)
    }
}
