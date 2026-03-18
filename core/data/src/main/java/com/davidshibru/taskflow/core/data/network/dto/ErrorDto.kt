package com.davidshibru.taskflow.core.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
@PublishedApi
internal data class ErrorDto(
    val errorcode: String?,
    val error: String?,
)