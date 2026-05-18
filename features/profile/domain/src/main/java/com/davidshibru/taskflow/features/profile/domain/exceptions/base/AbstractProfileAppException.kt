package com.davidshibru.taskflow.features.profile.domain.exceptions.base

import com.davidshibru.taskflow.core.essentials.exception.base.AbstractAppException
import com.davidshibru.taskflow.core.essentials.exception.base.WithLocalizedMessage
import com.davidshibru.taskflow.core.essentials.resources.StringProviderStore
import com.davidshibru.taskflow.features.profile.domain.resources.ProfileStringProvider

abstract class AbstractProfileAppException(
    message: String,
    cause: Throwable? = null,
) : AbstractAppException(message, cause), WithLocalizedMessage {

    override fun getLocalizedErrorMessage(stringProvider: StringProviderStore): String {
        return getLocalizedErrorMessage(stringProvider<ProfileStringProvider>())
    }

    abstract fun getLocalizedErrorMessage(stringProvider: ProfileStringProvider): String
}