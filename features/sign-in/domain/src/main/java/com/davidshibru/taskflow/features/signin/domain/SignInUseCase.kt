package com.davidshibru.taskflow.features.signin.domain

import com.davidshibru.taskflow.features.signin.domain.entities.Credentials
import com.davidshibru.taskflow.features.signin.domain.exceptions.InvalidCredentialsException
import com.davidshibru.taskflow.features.signin.domain.exceptions.EmptyFieldException


interface SignInUseCase {

    /**
     * @throws InvalidCredentialsException
     * @throws EmptyFieldException
     * */
    suspend operator fun invoke(credentials: Credentials)
}