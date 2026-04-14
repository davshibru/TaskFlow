package com.davidshibru.taskflow.glue.signup

import com.davidshibru.taskflow.data.AccountsDataRepository
import com.davidshibru.taskflow.feature.signup.domain.repositories.LoginAvailabilityRepository
import javax.inject.Inject

internal class SignUpLoginAvailabilityRepository @Inject constructor(
    private val accountsDataRepository: AccountsDataRepository,
) : LoginAvailabilityRepository {

    override suspend fun isLoginAvailable(login: String): Boolean {
        return accountsDataRepository.isLoginAvailable(login).unwrap()
    }

}