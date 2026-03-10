package com.davidshibru.taskflow.core.essentials.exception.base

import com.davidshibru.taskflow.core.essentials.resources.StringProviderStore
import com.davidshibru.taskflow.core.essentials.resources.CoreStringProvider

abstract class AbstractCoreAppException(
    message: String,
    cause: Throwable? = null,
): AbstractAppException(message, cause), WithLocalizedMessage{
    override fun getLocalizedErrorMessage(stringProvider: StringProviderStore): String {
        return getLocalizedErrorMessage(stringProvider<CoreStringProvider>())
    }

    abstract fun getLocalizedErrorMessage(stringProvider: CoreStringProvider): String
}