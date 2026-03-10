package com.davidshibru.taskflow.core.essentials.exception

import com.davidshibru.taskflow.core.essentials.exception.base.AbstractCoreAppException
import com.davidshibru.taskflow.core.essentials.resources.CoreStringProvider

class ConnectionException(
    cause: Throwable? = null,
) : AbstractCoreAppException("Network error", cause) {
    override fun getLocalizedErrorMessage(stringProvider: CoreStringProvider): String {
        return stringProvider.connectionErrorMessage
    }
}