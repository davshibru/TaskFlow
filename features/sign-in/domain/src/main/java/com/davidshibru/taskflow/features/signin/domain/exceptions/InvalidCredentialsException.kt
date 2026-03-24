package com.davidshibru.taskflow.features.signin.domain.exceptions

import com.davidshibru.taskflow.features.signin.domain.exceptions.base.AbstractSignInAppException
import com.davidshibru.taskflow.features.signin.domain.resources.SignInStringProvider


class InvalidCredentialsException(
    cause: Throwable? = null,
) : AbstractSignInAppException("Invalid login or password", cause) {
    override fun getLocalizedErrorMessage(stringProvider: SignInStringProvider): String {
        return stringProvider.invalidCredentialsError
    }

}