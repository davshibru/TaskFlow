package com.davidshibru.taskflow.features.main.domain.exceptions.base

import com.davidshibru.taskflow.core.essentials.exception.base.AbstractAppException
import com.davidshibru.taskflow.core.essentials.exception.base.WithLocalizedMessage
import com.davidshibru.taskflow.core.essentials.resources.StringProviderStore
import com.davidshibru.taskflow.features.main.domain.resources.MainStringProvider

abstract class AbstractMainAppException(
    message: String,
    cause: Throwable? = null,
) : AbstractAppException(message, cause), WithLocalizedMessage {

    override fun getLocalizedErrorMessage(stringProvider: StringProviderStore): String {
        return getLocalizedErrorMessage(stringProvider<MainStringProvider>())
    }

    abstract fun getLocalizedErrorMessage(stringProvider: MainStringProvider): String
}