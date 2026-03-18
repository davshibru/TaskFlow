package com.davidshibru.taskflow.features.signin.domain.exceptions.base

import com.davidshibru.taskflow.core.essentials.exception.base.AbstractAppException
import com.davidshibru.taskflow.core.essentials.exception.base.WithLocalizedMessage
import com.davidshibru.taskflow.core.essentials.resources.StringProviderStore
import com.davidshibru.taskflow.features.signin.domain.resources.SignInStringProvider

abstract class AbstractSignInAppException(
    message: String,
    cause: Throwable? = null,
): AbstractAppException(message, cause), WithLocalizedMessage {
    override fun getLocalizedErrorMessage(stringProvider: StringProviderStore): String {
        return getLocalizedErrorMessage(stringProvider<SignInStringProvider>())
    }

    abstract fun getLocalizedErrorMessage(stringProvider: SignInStringProvider): String
}