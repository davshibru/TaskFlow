package com.davidshibru.taskflow.feature.signup.domain.exceptions

import com.davidshibru.taskflow.feature.signup.domain.entities.InputField
import com.davidshibru.taskflow.feature.signup.domain.exceptions.base.AbstractValidationException
import com.davidshibru.taskflow.feature.signup.domain.resources.SignUpStringProvider

class InvalidRangeException(
    override val inputField: InputField.Number,
    val value: Int,
) : AbstractValidationException(
    message = "$inputField should be in range ${inputField.range}"
) {
    override fun getLocalizedErrorMessage(stringProvider: SignUpStringProvider): String {
        return stringProvider.invalidRangeError(inputField)
    }
}
