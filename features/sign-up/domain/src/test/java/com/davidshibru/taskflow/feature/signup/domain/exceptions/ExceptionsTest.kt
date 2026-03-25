package com.davidshibru.taskflow.feature.signup.domain.exceptions

import com.davidshibru.taskflow.feature.signup.domain.entities.InputField
import com.davidshibru.taskflow.feature.signup.domain.resources.SignUpStringProvider
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test

class ExceptionsTest {

    private val stringProvider = mockk<SignUpStringProvider>()

    @Test
    fun `EmptyFieldException should return localized message from provider`() {
        val field = InputField.Login
        val exception = EmptyFieldException(field)
        val expectedMessage = "Empty field error"
        every { stringProvider.emptyFieldError(field) } returns expectedMessage

        val actualMessage = exception.getLocalizedErrorMessage(stringProvider)

        assertEquals(expectedMessage, actualMessage)
    }

    @Test
    fun `TooShortValueException should return localized message from provider`() {
        val field = InputField.Login
        val exception = TooShortValueException(field, "abc")
        val expectedMessage = "Too short error"
        every { stringProvider.tooShortValueError(field) } returns expectedMessage

        val actualMessage = exception.getLocalizedErrorMessage(stringProvider)

        assertEquals(expectedMessage, actualMessage)
    }

    @Test
    fun `TooLongValueException should return localized message from provider`() {
        val field = InputField.Login
        val exception = TooLongValueException(field, "very_long_value")
        val expectedMessage = "Too long error"
        every { stringProvider.tooLongValueError(field) } returns expectedMessage

        val actualMessage = exception.getLocalizedErrorMessage(stringProvider)

        assertEquals(expectedMessage, actualMessage)
    }

    @Test
    fun `InvalidRangeException should return localized message from provider`() {
        val field = InputField.Age
        val exception = InvalidRangeException(field, 10)
        val expectedMessage = "Invalid range error"
        every { stringProvider.invalidRangeError(field) } returns expectedMessage

        val actualMessage = exception.getLocalizedErrorMessage(stringProvider)

        assertEquals(expectedMessage, actualMessage)
    }

    @Test
    fun `PasswordMismatchException should return localized message from provider`() {
        val exception = PasswordMismatchException()
        val expectedMessage = "Password mismatch error"
        every { stringProvider.passwordMismatchError } returns expectedMessage

        val actualMessage = exception.getLocalizedErrorMessage(stringProvider)

        assertEquals(expectedMessage, actualMessage)
    }

    @Test
    fun `LoginAlreadyExistsException should return localized message from provider`() {
        val exception = LoginAlreadyExistsException("taken_login")
        val expectedMessage = "Login exists error"
        every { stringProvider.loginAlreadyExistsError } returns expectedMessage

        val actualMessage = exception.getLocalizedErrorMessage(stringProvider)

        assertEquals(expectedMessage, actualMessage)
    }
}
