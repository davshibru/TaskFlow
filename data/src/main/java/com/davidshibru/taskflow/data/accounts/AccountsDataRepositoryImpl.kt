package com.davidshibru.taskflow.data.accounts

import com.davidshibru.taskflow.core.essentials.container.Container
import com.davidshibru.taskflow.core.essentials.container.Container.Loading.successContainer
import com.davidshibru.taskflow.core.essentials.container.catch
import com.davidshibru.taskflow.core.essentials.container.getValueOrNull
import com.davidshibru.taskflow.core.essentials.container.map
import com.davidshibru.taskflow.data.AccountsDataRepository
import com.davidshibru.taskflow.data.accounts.entities.AuthDataCredentials
import com.davidshibru.taskflow.data.accounts.entities.NewDataAccount
import com.davidshibru.taskflow.data.accounts.entities.NewDataAccount.Companion.displayName
import com.davidshibru.taskflow.data.accounts.exceptions.LoginIsNotAvailableDataException
import com.davidshibru.taskflow.data.accounts.remote.AccountsApi
import com.davidshibru.taskflow.data.accounts.remote.dto.CreateAccountRequestDto
import com.davidshibru.taskflow.data.accounts.remote.dto.SetDisplayNameRequestDto
import com.davidshibru.taskflow.data.accounts.remote.dto.SignInRequestDto
import com.davidshibru.taskflow.data.session.entities.AuthDataToken
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class AccountsDataRepositoryImpl @Inject constructor(
    private val accountsApi: AccountsApi,
) : AccountsDataRepository {
    override suspend fun signIn(
        credentials: AuthDataCredentials,
    ): Container<AuthDataToken.Default> {
        val request = SignInRequestDto(
            user = credentials.login,
            password = credentials.password,
        )
        return accountsApi.signIn(request).map { responseDto ->
                AuthDataToken.Default(accessToken = responseDto.accessToken)
            }
    }

    override suspend fun isLoginAvailable(login: String): Container.Completed<Boolean> {
        return accountsApi
            .isLoginAvailable(login)
            .map { it.available }
            .catch(LoginIsNotAvailableDataException::class) {
                successContainer(false)
            }
    }

    override suspend fun createAccount(account: NewDataAccount): Container.Completed<Unit> {
        val request = CreateAccountRequestDto(
            username = account.login,
            password = account.password,
        )

        val container = accountsApi.createAccount(request)

        val response = container.getValueOrNull()

        if (response != null) {
            accountsApi.setDisplayNameAfterSignUp(
                userId = response.userId,
                authHeaderValue = "Bearer ${response.accessToken}",
                body = SetDisplayNameRequestDto(account.displayName())
            )
        }
        return container.map { Unit }
    }
}
