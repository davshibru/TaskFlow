package com.davidshibru.taskflow.feature.signup.demo

import com.davidshibru.taskflow.feature.signup.domain.entities.NewAccount
import com.davidshibru.taskflow.feature.signup.domain.repositories.CreateAccountRepository
import kotlinx.coroutines.delay

internal class DemoCreateAccountRepository : CreateAccountRepository {

    override suspend fun createAccount(account: NewAccount){
        delay(1000)
    }
}