package com.davidshibru.taskflow.feature.signup.domain.usecases

import com.davidshibru.taskflow.feature.signup.domain.entities.NewAccount
import com.davidshibru.taskflow.feature.signup.domain.entities.ValidationResult
import com.davidshibru.taskflow.feature.signup.domain.exceptions.PasswordMismatchException
import com.davidshibru.taskflow.feature.signup.domain.repositories.CreateAccountRepository
import com.davidshibru.taskflow.feature.signup.domain.validators.NewAccountValidator
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class SignUpUseCaseTest {

    private lateinit var newAccountValidator: NewAccountValidator
    private lateinit var createAccountRepository: CreateAccountRepository
    private lateinit var signUpUseCase: SignUpUseCaseImpl

    @Before
    fun setUp() {
        newAccountValidator = mockk()
        createAccountRepository = mockk(relaxed = true)
        signUpUseCase = SignUpUseCaseImpl(newAccountValidator, createAccountRepository)
    }

    @Test
    fun `GIVEN valid account WHEN invoke THEN call createAccount`() = runTest {
        val account = mockk<NewAccount>()
        coEvery { newAccountValidator.validate(account) } returns ValidationResult.Success

        signUpUseCase.invoke(account)

        coVerify { createAccountRepository.createAccount(account) }
    }

    @Test(expected = PasswordMismatchException::class)
    fun `GIVEN invalid account WHEN invoke THEN throw first exception`() = runTest {
        val account = mockk<NewAccount>()
        val exception = PasswordMismatchException()
        coEvery { newAccountValidator.validate(account) } returns ValidationResult.Failure(exception)

        signUpUseCase.invoke(account)
    }
}
