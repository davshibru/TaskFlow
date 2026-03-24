package com.davidshibru.taskflow.glue.signin.mapper

import com.davidshibru.taskflow.data.session.entities.AuthDataToken
import com.davidshibru.taskflow.features.signin.domain.entities.Token
import javax.inject.Inject

internal interface MapperToken {
    fun toToken(token: AuthDataToken): Token
    fun toAuthDataToken(origin: Token): AuthDataToken

    class Default @Inject constructor() : MapperToken {
        override fun toToken(token: AuthDataToken): Token {
            return if (token is AuthDataToken.Default) {
                Token(token = token.accessToken)
            } else {
                Token("")
            }
        }

        override fun toAuthDataToken(origin: Token): AuthDataToken {
            return AuthDataToken.Default(
                accessToken = origin.token,
            )
        }
    }
}