package com.davidshibru.taskflow.glue.init

import com.davidshibru.taskflow.data.SessionProvider
import com.davidshibru.taskflow.data.session.entities.AuthDataToken
import com.davidshibru.taskflow.feature.init.domain.repositories.AuthRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class InitAuthRepository @Inject constructor(
    private val sessionProvider: SessionProvider,
) : AuthRepository {
    override suspend fun isAuthorized(): Boolean {
        return sessionProvider.getToken().map { it !is AuthDataToken.Empty }.first()
    }
}