package com.davidshibru.taskflow.feature.signup.domain.validators

import com.davidshibru.taskflow.feature.signup.domain.entities.NewAccount
import com.davidshibru.taskflow.feature.signup.domain.entities.ValidationResult
import com.davidshibru.taskflow.feature.signup.domain.exceptions.LoginAlreadyExistsException
import com.davidshibru.taskflow.feature.signup.domain.exceptions.PasswordMismatchException
import com.davidshibru.taskflow.feature.signup.domain.repositories.LoginAvailabilityRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class NewAccountValidatorTest {

    private lateinit var loginAvailabilityRepository: LoginAvailabilityRepository
    private lateinit var validator: NewAccountValidatorImpl

    @Before
    fun setUp() {
        loginAvailabilityRepository = mockk()
        validator = NewAccountValidatorImpl(loginAvailabilityRepository)
    }

    @Test
    fun `GIVEN valid account WHEN validate THEN return success`() = runTest {
        val account = NewAccount("valid_login", "password123", "password123", "First", "Last", 25)
        coEvery { loginAvailabilityRepository.isLoginAvailable(any()) } returns true

        val result = validator.validate(account)

        assertEquals(ValidationResult.Success, result)
    }

    @Test
    fun `GIVEN password mismatch WHEN validate THEN return failure with PasswordMismatchException`() = runTest {
        val account = NewAccount("valid_login", "password123", "mismatch", "First", "Last", 25)
        coEvery { loginAvailabilityRepository.isLoginAvailable(any()) } returns true

        val result = validator.validate(account)

        assertTrue(result is ValidationResult.Failure)
        val failure = result as ValidationResult.Failure
        assertTrue(failure.exceptions.any { it is PasswordMismatchException })
    }

    @Test
    fun `GIVEN unavailable login WHEN validate THEN return failure with LoginAlreadyExistsException`() = runTest {
        val account = NewAccount("taken_login", "password123", "password123", "First", "Last", 25)
        coEvery { loginAvailabilityRepository.isLoginAvailable("taken_login") } returns false

        val result = validator.validate(account)

        assertTrue(result is ValidationResult.Failure)
        val failure = result as ValidationResult.Failure
        assertTrue(failure.exceptions.any { it is LoginAlreadyExistsException })
    }
}
