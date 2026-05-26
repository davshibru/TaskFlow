package com.davidshibru.taskflow.feature.chats.domain.exceptions.base

import com.davidshibru.taskflow.core.essentials.exception.base.AbstractAppException
import com.davidshibru.taskflow.core.essentials.exception.base.WithLocalizedMessage
import com.davidshibru.taskflow.core.essentials.resources.StringProviderStore
import com.davidshibru.taskflow.feature.chats.domain.resources.ChatsStringProvider

abstract class AbstractChatAppException(
    message: String,
    cause: Throwable? = null,
): AbstractAppException(message, cause), WithLocalizedMessage {
    override fun getLocalizedErrorMessage(stringProvider: StringProviderStore): String {
        return getLocalizedErrorMessage(stringProvider<ChatsStringProvider>())
    }

    abstract fun getLocalizedErrorMessage(stringProvider: ChatsStringProvider): String
}