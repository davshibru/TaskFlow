package com.davidshibru.taskflow.feature.chats.domain.exceptions

import com.davidshibru.taskflow.feature.chats.domain.entities.ChatId
import com.davidshibru.taskflow.feature.chats.domain.exceptions.base.AbstractChatAppException
import com.davidshibru.taskflow.feature.chats.domain.resources.ChatsStringProvider

class ChatNotFoundException(
    val chatId: ChatId,
    cause: Throwable,
) : AbstractChatAppException(
    message = "Chat with id=$chatId has not been found.",
    cause = cause,
) {
    override fun getLocalizedErrorMessage(stringProvider: ChatsStringProvider): String {
        return stringProvider.chatNotFoundErrorMessage(chatId)
    }


}