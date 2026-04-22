package com.davidshibru.taskflow.feature.chats.domain.effects

import com.davidshibru.taskflow.feature.chats.domain.entities.Chat

interface ChatsUserChoices {

    suspend fun confirmChatRemoval(chat: Chat): Boolean
}