package com.davidshibru.taskflow.feature.signup.domain

import com.davidshibru.taskflow.feature.signup.domain.entities.NewAccount
import com.davidshibru.taskflow.feature.signup.domain.exceptions.*

interface SignUpUseCase {

    /**
     * @throws EmptyFieldException
     * @throws InvalidRangeException
     * @throws LoginAlreadyExistsException
     * @throws PasswordMismatchException
     * @throws TooLongValueException
     * @throws TooShortValueException
     * */
    suspend operator fun invoke(account: NewAccount)

}