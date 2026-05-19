package com.davidshibru.taskflow.data.session.entities

import com.davidshibru.taskflow.core.essentials.entities.UserId
import kotlin.io.encoding.Base64

sealed class AuthDataToken {

    data class Default(
        val accessToken: String,
        val host: String = "localhost",
    ) : AuthDataToken()

    data object Empty : AuthDataToken()
}

fun AuthDataToken.Default.getUserId(): UserId {
    return accessToken.split("_")[1]
        .let { base64.decode(it).decodeToString() }
        .let { UserId("@$it:$host") }
}

private val base64 = Base64.withPadding(Base64.PaddingOption.PRESENT_OPTIONAL)