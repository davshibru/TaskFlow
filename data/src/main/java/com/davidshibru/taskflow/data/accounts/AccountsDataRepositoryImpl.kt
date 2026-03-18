package com.davidshibru.taskflow.data.accounts

import com.davidshibru.taskflow.core.data.network.containerOf
import com.davidshibru.taskflow.core.essentials.container.map
import com.davidshibru.taskflow.data.AccountsDataRepository
import com.davidshibru.taskflow.data.accounts.entities.AuthDataCredentials
import com.davidshibru.taskflow.data.accounts.remote.AccountsApi
import com.davidshibru.taskflow.data.accounts.remote.dto.SignInRequestDto
import com.davidshibru.taskflow.data.session.entities.AuthDataToken
import javax.inject.Inject

internal class AccountsDataRepositoryImpl @Inject constructor(
    private val accountsApi: AccountsApi,
) : AccountsDataRepository {
    override suspend fun signIn(
        credentials: AuthDataCredentials,
    ): AuthDataToken.Default {
        val request = SignInRequestDto(
            user = credentials.login,
            password = credentials.password,
        )
        return containerOf { accountsApi.signIn(request) }
            .map { responseDto ->
                AuthDataToken.Default(accessToken = responseDto.accessToken)
            }
            .unwrap()
    }
}