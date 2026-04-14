package com.davidshibru.taskflow.data.accounts.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class IsLoginAvailableResponseDto(
    val available: Boolean,
)