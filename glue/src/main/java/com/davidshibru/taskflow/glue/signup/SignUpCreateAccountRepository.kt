package com.davidshibru.taskflow.glue.signup

import com.davidshibru.taskflow.data.AccountsDataRepository
import com.davidshibru.taskflow.feature.signup.domain.entities.NewAccount
import com.davidshibru.taskflow.feature.signup.domain.repositories.CreateAccountRepository
import com.davidshibru.taskflow.glue.signup.mapper.MapperNewAccount
import javax.inject.Inject

internal class SignUpCreateAccountRepository @Inject constructor(
    private val mapperNewAccount: MapperNewAccount,
    private val accountDataRepository: AccountsDataRepository,
) : CreateAccountRepository {

    override suspend fun createAccount(account: NewAccount) {
        accountDataRepository
            .createAccount(mapperNewAccount.toNewDataAccount(account))
            .unwrap()
    }
}