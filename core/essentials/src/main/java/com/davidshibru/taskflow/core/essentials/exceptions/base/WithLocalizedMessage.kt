package com.davidshibru.taskflow.core.essentials.exceptions.base

import com.davidshibru.taskflow.core.essentials.resources.StringProviderStore

interface WithLocalizedMessage {
    fun getLocalizedErrorMessage(stringProviderStore: StringProviderStore): String
}