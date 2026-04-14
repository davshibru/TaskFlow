package com.davidshibru.taskflow.data.accounts.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CreateAccountResponseDto(
    val accessToken: String,
    val deviceId: String,
    val userId: String,
)