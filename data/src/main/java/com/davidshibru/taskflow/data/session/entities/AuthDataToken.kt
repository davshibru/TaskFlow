package com.davidshibru.taskflow.data.session.entities

sealed class AuthDataToken {

    data class Default(
        val accessToken: String,
    ) : AuthDataToken()

    data object Empty : AuthDataToken()
}