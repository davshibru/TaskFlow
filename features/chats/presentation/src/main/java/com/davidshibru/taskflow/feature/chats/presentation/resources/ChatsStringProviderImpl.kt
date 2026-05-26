package com.davidshibru.taskflow.feature.chats.presentation.resources

import android.content.Context
import com.davidshibru.taskflow.feature.chats.domain.entities.Chat
import com.davidshibru.taskflow.feature.chats.domain.entities.ChatId
import com.davidshibru.taskflow.feature.chats.domain.resources.ChatsStringProvider
import com.davidshibru.taskflow.feature.chats.presentation.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ChatsStringProviderImpl @Inject constructor(
    @param:ApplicationContext private val context: Context,
) : ChatsStringProvider {

    override val confirmDeleteDialogTitle = context.getString(R.string.chats_chat_removal)

    override fun confirmDeleteDialogMessage(chat: Chat) =
        context.getString(R.string.chats_confirm_delete_message, chat.title)

    override fun chatNotFoundErrorMessage(chatId: ChatId): String {
        return context.getString(R.string.chats_not_found_error, chatId)
    }
}