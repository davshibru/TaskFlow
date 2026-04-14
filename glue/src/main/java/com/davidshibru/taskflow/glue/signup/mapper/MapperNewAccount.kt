package com.davidshibru.taskflow.glue.signup.mapper

import com.davidshibru.taskflow.data.accounts.entities.NewDataAccount
import com.davidshibru.taskflow.feature.signup.domain.entities.NewAccount
import javax.inject.Inject

internal interface MapperNewAccount {
    fun toNewDataAccount(account: NewAccount): NewDataAccount

    class Default @Inject constructor() : MapperNewAccount {
        override fun toNewDataAccount(account: NewAccount): NewDataAccount {
            return with(account) {
                NewDataAccount(
                    login = login,
                    password = password,
                    firstName = firstName,
                    lastName = lastName,
                )
            }
        }

    }
}