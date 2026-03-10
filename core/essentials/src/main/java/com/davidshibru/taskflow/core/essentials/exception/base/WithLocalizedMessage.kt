package com.davidshibru.taskflow.core.essentials.exception.base

import com.davidshibru.taskflow.core.essentials.resources.StringProviderStore

interface WithLocalizedMessage {
    fun getLocalizedErrorMessage(stringProvider: StringProviderStore): String
}