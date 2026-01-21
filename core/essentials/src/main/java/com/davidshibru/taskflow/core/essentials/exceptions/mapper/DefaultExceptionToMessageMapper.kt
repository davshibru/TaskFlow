package com.davidshibru.taskflow.core.essentials.exceptions.mapper

import com.davidshibru.taskflow.core.essentials.exceptions.base.WithLocalizedMessage
import com.davidshibru.taskflow.core.essentials.resources.CoreStringProvider
import com.davidshibru.taskflow.core.essentials.resources.StringProviderStore
import javax.inject.Inject


class DefaultExceptionToMessageMapper @Inject constructor(
    private val stringProviderStore: StringProviderStore
) : ExceptionToMessageMapper{

    override fun getLocalizedMessage(exception: Exception): String {
         return if (exception is WithLocalizedMessage) {
             exception.getLocalizedErrorMessage(stringProviderStore)
         } else {
             stringProviderStore<CoreStringProvider>().unknownErrorMessage
         }
    }
}