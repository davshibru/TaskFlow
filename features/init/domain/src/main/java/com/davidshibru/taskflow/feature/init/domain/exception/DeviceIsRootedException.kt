package com.davidshibru.taskflow.feature.init.domain.exception

import com.davidshibru.taskflow.feature.init.domain.exception.base.InitAppException
import com.davidshibru.taskflow.feature.init.domain.resources.InitStringProvider

class DeviceIsRootedException : InitAppException("Device is rooted") {
    override fun getLocalizedErrorMessage(stringProvider: InitStringProvider): String {
        return stringProvider.deviceIsRootedMessage
    }
}