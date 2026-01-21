package com.davidshibru.taskflow.core.essentials.exceptions

import com.davidshibru.taskflow.core.essentials.exceptions.base.CoreAppException
import com.davidshibru.taskflow.core.essentials.resources.CoreStringProvider

class ConnectionException(
    cause: Throwable? = null,
) : CoreAppException("Network error", cause) {
    override fun getLocalizedErrorMessage(stringProvider: CoreStringProvider): String {
        return stringProvider.connectionErrorMessage
    }
}