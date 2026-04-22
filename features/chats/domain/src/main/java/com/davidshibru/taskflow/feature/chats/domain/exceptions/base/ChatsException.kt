package com.davidshibru.taskflow.feature.chats.domain.exceptions.base

sealed class ChatsException : Exception() {
    class Default(override val message: String? = null) : ChatsException()
}