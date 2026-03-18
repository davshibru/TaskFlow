package com.davidshibru.taskflow.features.signin.domain.exceptions

import com.davidshibru.taskflow.features.signin.domain.entities.InputField
import com.davidshibru.taskflow.features.signin.domain.exceptions.base.AbstractSignInAppException
import com.davidshibru.taskflow.features.signin.domain.resources.SignInStringProvider

class EmptyFieldException(
    val inputField: InputField
) : AbstractSignInAppException("Empty field") {
    override fun getLocalizedErrorMessage(stringProvider: SignInStringProvider): String {
        return stringProvider.emptyFieldError(inputField)

    }
}