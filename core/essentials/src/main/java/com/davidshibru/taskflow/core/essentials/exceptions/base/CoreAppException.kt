package com.davidshibru.taskflow.core.essentials.exceptions.base

import com.davidshibru.taskflow.core.essentials.resources.CoreStringProvider
import com.davidshibru.taskflow.core.essentials.resources.StringProviderStore

abstract class CoreAppException(
    message: String,
    cause: Throwable? = null,
) : AppExceptions(message, cause), WithLocalizedMessage {

    override fun getLocalizedErrorMessage(stringProviderStore: StringProviderStore): String {
        return getLocalizedErrorMessage(stringProviderStore<CoreStringProvider>())
    }

    abstract fun getLocalizedErrorMessage(stringProvider: CoreStringProvider) : String
}
