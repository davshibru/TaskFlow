package com.davidshibru.taskflow.glue.signin

import com.davidshibru.taskflow.data.SessionManager
import com.davidshibru.taskflow.features.signin.domain.entities.Token
import com.davidshibru.taskflow.features.signin.domain.repositories.LocalTokenRepository
import com.davidshibru.taskflow.glue.signin.mapper.MapperToken
import javax.inject.Inject

internal class SignInLocalTokenRepository @Inject constructor(
    private val mapperToken: MapperToken,
    private val sessionManager: SessionManager,
) : LocalTokenRepository {
    override suspend fun saveToken(token: Token) {
        sessionManager.saveToken(mapperToken.toAuthDataToken(token))
    }
}
