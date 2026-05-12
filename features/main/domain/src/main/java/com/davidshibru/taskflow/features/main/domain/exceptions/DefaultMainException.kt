package com.davidshibru.taskflow.features.main.domain.exceptions

import com.davidshibru.taskflow.features.main.domain.exceptions.base.AbstractMainAppException
import com.davidshibru.taskflow.features.main.domain.resources.MainStringProvider

class DefaultMainException(
    message: String = "Default Main error",
    cause: Throwable? = null,
) : AbstractMainAppException(message, cause) {

    override fun getLocalizedErrorMessage(stringProvider: MainStringProvider): String {
        return stringProvider.defaultErrorMessage
    }
}