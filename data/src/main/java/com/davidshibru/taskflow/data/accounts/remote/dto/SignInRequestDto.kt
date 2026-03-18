package com.davidshibru.taskflow.data.accounts.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class SignInRequestDto(
    val type: String = "m.login.password",
    val user: String,
    val password: String,
)