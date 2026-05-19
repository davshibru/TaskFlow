package com.davidshibru.taskflow.data

import com.davidshibru.taskflow.core.essentials.container.Container.Completed
import com.davidshibru.taskflow.core.essentials.container.Container.Loading.errorContainer
import com.davidshibru.taskflow.core.essentials.container.Container.Loading.successContainer
import com.davidshibru.taskflow.core.essentials.entities.UserId
import com.davidshibru.taskflow.core.essentials.exception.AuthException
import com.davidshibru.taskflow.data.session.entities.AuthDataToken
import com.davidshibru.taskflow.data.session.entities.getUserId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

interface SessionProvider {

    fun getToken(): Flow<AuthDataToken>
}


fun SessionProvider.getValidToken(): Flow<Completed<AuthDataToken.Default>> {
    return getToken().map { token ->
        when (token) {
            is AuthDataToken.Default -> successContainer(token)
            AuthDataToken.Empty -> errorContainer(AuthException())
        }
    }
}

suspend fun SessionProvider.getCurrentUserId(): UserId =
    getValidToken().first().unwrap().getUserId()
