package com.davidshibru.taskflow.core.essentials.exceptions

import com.davidshibru.taskflow.core.essentials.exceptions.base.CoreAppException
import com.davidshibru.taskflow.core.essentials.resources.CoreStringProvider

class BackendException(
    val httpCode: Int,
    val backendMessage: String,
    cause: Throwable? = null,
) : CoreAppException("Server error", cause) {
    override fun getLocalizedErrorMessage(stringProvider: CoreStringProvider): String {
        return stringProvider.backendErrorMessage(httpCode, backendMessage)
    }
}