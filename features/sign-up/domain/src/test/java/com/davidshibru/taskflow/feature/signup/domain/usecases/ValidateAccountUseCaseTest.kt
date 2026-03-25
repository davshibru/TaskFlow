package com.davidshibru.taskflow.feature.signup.domain.usecases

import com.davidshibru.taskflow.feature.signup.domain.entities.NewAccount
import com.davidshibru.taskflow.feature.signup.domain.entities.ValidationResult
import com.davidshibru.taskflow.feature.signup.domain.exceptions.PasswordMismatchException
import com.davidshibru.taskflow.feature.signup.domain.validators.NewAccountValidator
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertSame
import org.junit.Before
import org.junit.Test

class ValidateAccountUseCaseTest {

    @RelaxedMockK
    private lateinit var newAccountValidator: NewAccountValidator

    private lateinit var useCase: ValidateAccountUseCaseImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = ValidateAccountUseCaseImpl(newAccountValidator)
    }

    @Test
    fun `GIVEN any account WHEN invoke THEN delegate account to validator`() = runTest {
        // given
        val account = mockk<NewAccount>()
        coEvery { newAccountValidator.validate(any()) } returns ValidationResult.Success

        // when
        useCase.invoke(account)

        // then
        coVerify(exactly = 1) {
            newAccountValidator.validate(account)
        }
    }

    @Test
    fun `GIVEN valid account WHEN invoke THEN return valid result from validator`() = runTest {
        // given
        val account = mockk<NewAccount>()
        val expectedResult = ValidationResult.Success
        coEvery { newAccountValidator.validate(any()) } returns expectedResult

        // when
        val actualResult = useCase.invoke(account)

        // then
        assertSame(expectedResult, actualResult)
    }

    @Test
    fun `GIVEN invalid account WHEN invoke THEN return failure result from validator`() = runTest {
        // given
        val account = mockk<NewAccount>()
        val expectedResult = ValidationResult.Failure(PasswordMismatchException())
        coEvery { newAccountValidator.validate(any()) } returns expectedResult

        // when
        val actualResult = useCase.invoke(account)

        // then
        assertSame(expectedResult, actualResult)
    }
}
