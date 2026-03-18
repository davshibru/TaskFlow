package com.davidshibru.taskflow.features.signin.domain.exceptions

import com.davidshibru.taskflow.features.signin.domain.exceptions.base.AbstractSignInAppException
import com.davidshibru.taskflow.features.signin.domain.resources.SignInStringProvider


class InvalidCredentialsException : AbstractSignInAppException("Invalid login or password") {
    override fun getLocalizedErrorMessage(stringProvider: SignInStringProvider): String {
        return stringProvider.invalidCredentialsError
    }

}