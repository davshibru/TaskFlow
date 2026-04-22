package com.davidshibru.taskflow.feature.chats.domain.effects

import com.davidshibru.taskflow.core.essentials.dialogs.DialogConfig
import com.davidshibru.taskflow.core.essentials.dialogs.Dialogs
import com.davidshibru.taskflow.core.essentials.resources.CoreStringProvider
import com.davidshibru.taskflow.feature.chats.domain.entities.Chat
import com.davidshibru.taskflow.feature.chats.domain.resources.ChatsStringProvider
import javax.inject.Inject

class ChatsUserChoicesImpl @Inject constructor(
    private val dialogs: Dialogs,
    private val chatsStringProvider: ChatsStringProvider,
    private val coreStringProvider: CoreStringProvider,
) : ChatsUserChoices {

    override suspend fun confirmChatRemoval(chat: Chat): Boolean {
        val config = DialogConfig(
            title = chatsStringProvider.confirmDeleteDialogTitle,
            message = chatsStringProvider.confirmDeleteDialogMessage(chat),
            positiveButton = coreStringProvider.deleteAction,
            negativeButton = coreStringProvider.cancelAction,
        )
        return dialogs.showAlertDialog(config)
    }
}