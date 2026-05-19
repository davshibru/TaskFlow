package com.davidshibru.taskflow.core.essentials.exception

import com.davidshibru.taskflow.core.essentials.exception.base.AbstractCoreAppException
import com.davidshibru.taskflow.core.essentials.resources.CoreStringProvider

class AuthException(
    cause: Throwable? = null,
) : AbstractCoreAppException("User session has been expired", cause) {
    override fun getLocalizedErrorMessage(stringProvider: CoreStringProvider): String {
        return stringProvider.authErrorMessage
    }
}