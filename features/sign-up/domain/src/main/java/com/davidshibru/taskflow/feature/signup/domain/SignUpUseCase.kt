package com.davidshibru.taskflow.feature.signup.domain

import com.davidshibru.taskflow.feature.signup.domain.entities.NewAccount

interface SignUpUseCase {

    suspend operator fun invoke(account: NewAccount)

}