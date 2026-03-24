package com.davidshibru.taskflow.feature.signup.domain.exceptions.base

import com.davidshibru.taskflow.core.essentials.exception.base.AbstractAppException
import com.davidshibru.taskflow.core.essentials.exception.base.WithLocalizedMessage
import com.davidshibru.taskflow.core.essentials.resources.StringProviderStore
import com.davidshibru.taskflow.feature.signup.domain.resources.SignUpStringProvider

abstract class AbstractSignUpAppException(
    message: String,
    cause: Throwable? = null,
) : AbstractAppException(message, cause), WithLocalizedMessage {

    override fun getLocalizedErrorMessage(stringProviderStore: StringProviderStore): String {
        return getLocalizedErrorMessage(stringProviderStore<SignUpStringProvider>())
    }

    abstract fun getLocalizedErrorMessage(stringProvider: SignUpStringProvider): String

}