package com.davidshibru.taskflow.data.accounts.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CreateAccountRequestDto(
    val username: String,
    val password: String,
    val auth: AuthRequestDto = AuthRequestDto(),
    val refreshToken: Boolean = false,
)

@Serializable
data class AuthRequestDto(
    val type: String = "m.login.dummy",
)