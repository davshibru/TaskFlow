package com.davidshibru.taskflow.data.accounts.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class SetDisplayNameRequestDto(
    val displayname: String
)