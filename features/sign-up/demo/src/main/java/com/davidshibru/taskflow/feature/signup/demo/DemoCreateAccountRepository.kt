package com.davidshibru.taskflow.feature.signup.demo

import com.davidshibru.taskflow.feature.signup.domain.entities.NewAccount
import com.davidshibru.taskflow.feature.signup.domain.repositories.CreateAccountRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

internal class DemoCreateAccountRepository  @Inject constructor(): CreateAccountRepository {

    override suspend fun createAccount(account: NewAccount){
        delay(1000)
    }
}