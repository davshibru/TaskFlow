package com.davidshibru.taskflow.core.essentials.exception

import com.davidshibru.taskflow.core.essentials.exception.base.AbstractCoreAppException
import com.davidshibru.taskflow.core.essentials.resources.CoreStringProvider

class InvalidBackendResponseException(
    cause: Throwable
) : AbstractCoreAppException("Can't parse server response", cause) {
    override fun getLocalizedErrorMessage(stringProvider: CoreStringProvider): String {
        return stringProvider.invalidBackendResponseMessage
    }
}