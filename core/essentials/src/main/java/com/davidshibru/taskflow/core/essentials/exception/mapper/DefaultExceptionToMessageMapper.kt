package com.davidshibru.taskflow.core.essentials.exception.mapper

import com.davidshibru.taskflow.core.essentials.resources.StringProviderStore
import com.davidshibru.taskflow.core.essentials.exception.base.WithLocalizedMessage
import com.davidshibru.taskflow.core.essentials.resources.CoreStringProvider
import javax.inject.Inject

class DefaultExceptionToMessageMapper @Inject constructor(
    private val stringProviderStore: StringProviderStore,
) : ExceptionToMessageMapper {
    override fun getLocalizedMessage(exception: Exception): String {
        return if (exception is WithLocalizedMessage) {
            exception.getLocalizedErrorMessage(stringProviderStore)
        } else {
            stringProviderStore<CoreStringProvider>().unknownErrorMessage
        }
    }

}