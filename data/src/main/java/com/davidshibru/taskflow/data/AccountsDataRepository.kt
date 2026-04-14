package com.davidshibru.taskflow.data

import com.davidshibru.taskflow.core.essentials.container.Container
import com.davidshibru.taskflow.data.accounts.entities.AuthDataCredentials
import com.davidshibru.taskflow.data.accounts.entities.NewDataAccount
import com.davidshibru.taskflow.data.session.entities.AuthDataToken
import com.davidshibru.taskflow.data.accounts.exceptions.InvalidCredentialsDataException

interface AccountsDataRepository {

    /**
     * @throws InvalidCredentialsDataException
     * */
    suspend fun signIn(credentials: AuthDataCredentials): Container<AuthDataToken.Default>

    suspend fun isLoginAvailable(
        login: String
    ): Container.Completed<Boolean>

    suspend fun createAccount(
        account: NewDataAccount,
    ): Container.Completed<Unit>
}