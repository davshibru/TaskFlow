package com.davidshibru.taskflow.core.essentials.exception

import com.davidshibru.taskflow.core.essentials.exception.base.AbstractCoreAppException
import com.davidshibru.taskflow.core.essentials.resources.CoreStringProvider


class BackendException(
    val code: Int = 400,
    val backendMessage: String = "",
    cause: Throwable? = null
) : AbstractCoreAppException("Server error", cause) {
    override fun getLocalizedErrorMessage(stringProvider: CoreStringProvider): String {
        return stringProvider.backendErrorMessage(code, backendMessage)
    }
}