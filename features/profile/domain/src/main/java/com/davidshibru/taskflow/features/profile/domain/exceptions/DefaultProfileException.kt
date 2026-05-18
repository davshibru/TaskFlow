package com.davidshibru.taskflow.features.profile.domain.exceptions

import com.davidshibru.taskflow.features.profile.domain.exceptions.base.AbstractProfileAppException
import com.davidshibru.taskflow.features.profile.domain.resources.ProfileStringProvider

class DefaultProfileException(
    message: String = "Default Profile error",
    cause: Throwable? = null,
) : AbstractProfileAppException(message, cause) {

    override fun getLocalizedErrorMessage(stringProvider: ProfileStringProvider): String {
        return stringProvider.defaultErrorMessage
    }
}