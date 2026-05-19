package com.davidshibru.taskflow.core.data.network.dto

import com.davidshibru.taskflow.core.essentials.entities.UserId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class EventDto<T>(
    val type: String,
    val content: T,
    @SerialName("user_id") private val _userId: String,
) {

    @Transient
    val userId: UserId = UserId(_userId)
}
