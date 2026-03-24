package com.davidshibru.taskflow.feature.signup.domain.repositories

import com.davidshibru.taskflow.feature.signup.domain.entities.NewAccount

interface CreateAccountRepository {

    suspend fun createAccount(account: NewAccount)
}