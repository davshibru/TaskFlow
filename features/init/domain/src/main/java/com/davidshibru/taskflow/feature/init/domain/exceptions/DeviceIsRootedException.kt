package com.davidshibru.taskflow.feature.init.domain.exceptions


import com.davidshibru.taskflow.core.essentials.exception.base.AbstractAppException
import com.davidshibru.taskflow.core.essentials.resources.StringProviderStore
import com.davidshibru.taskflow.core.essentials.exception.base.WithLocalizedMessage
import com.davidshibru.taskflow.feature.init.domain.resources.InitStringProvider

abstract class InitAppException(
    message: String,
    cause: Throwable? = null,
): AbstractAppException(message, cause), WithLocalizedMessage{
    override fun getLocalizedErrorMessage(stringProvider: StringProviderStore): String {
        return getLocalizedErrorMessage(stringProvider<InitStringProvider>())
    }

    abstract fun getLocalizedErrorMessage(stringProvider: InitStringProvider): String
}


class DeviceIsRootedException : InitAppException("Device is rooted") {
    override fun getLocalizedErrorMessage(stringProvider: InitStringProvider): String {
        return stringProvider.deviceIsRootedErrorMessage
    }
}