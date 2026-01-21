package com.davidshibru.taskflow.feature.init.domain

import com.davidshibru.taskflow.core.essentials.exceptions.base.AppExceptions
import com.davidshibru.taskflow.core.essentials.resources.StringProviderStore
import com.davidshibru.taskflow.core.essentials.exceptions.base.WithLocalizedMessage


abstract class InitAppException(
    message: String,
    cause: Throwable? = null,
) : AppExceptions(message, cause), WithLocalizedMessage {

    override fun getLocalizedErrorMessage(stringProviderStore: StringProviderStore): String {
        return getLocalizedErrorMessage(stringProviderStore<InitStringProvider>())
    }
    abstract fun getLocalizedErrorMessage(stringProvider: InitStringProvider) : String
}

class DeviceIsRootedException : InitAppException("Device is rooted") {
    override fun getLocalizedErrorMessage(stringProvider: InitStringProvider): String {
        return stringProvider.deviceIsRootedMessage
    }
}