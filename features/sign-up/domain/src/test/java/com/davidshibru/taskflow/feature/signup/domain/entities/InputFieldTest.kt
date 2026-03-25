package com.davidshibru.taskflow.feature.signup.domain.entities

import com.davidshibru.taskflow.feature.signup.domain.exceptions.EmptyFieldException
import com.davidshibru.taskflow.feature.signup.domain.exceptions.InvalidRangeException
import com.davidshibru.taskflow.feature.signup.domain.exceptions.TooLongValueException
import com.davidshibru.taskflow.feature.signup.domain.exceptions.TooShortValueException
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class InputFieldTest {

    @Test
    fun `Login validation Success`() {
        val result = InputField.Login.validate("validLogin")
        assertEquals(ValidationResult.Success, result)
    }

    @Test
    fun `Login validation EmptyFieldException`() {
        val result = InputField.Login.validate("  ")
        assertTrue(result is ValidationResult.Failure)
        assertTrue((result as ValidationResult.Failure).exceptions.first() is EmptyFieldException)
    }

    @Test
    fun `Login validation TooShortValueException`() {
        val result = InputField.Login.validate("abc")
        assertTrue(result is ValidationResult.Failure)
        assertTrue((result as ValidationResult.Failure).exceptions.first() is TooShortValueException)
    }

    @Test
    fun `Login validation TooLongValueException`() {
        val result = InputField.Login.validate("a".repeat(17))
        assertTrue(result is ValidationResult.Failure)
        assertTrue((result as ValidationResult.Failure).exceptions.first() is TooLongValueException)
    }

    @Test
    fun `Age validation Success`() {
        val result = InputField.Age.validate(25)
        assertEquals(ValidationResult.Success, result)
    }

    @Test
    fun `Age validation InvalidRangeException`() {
        val result = InputField.Age.validate(10)
        assertTrue(result is ValidationResult.Failure)
        assertTrue((result as ValidationResult.Failure).exceptions.first() is InvalidRangeException)
    }

    @Test
    fun `Password validation Success`() {
        val result = InputField.Password.validate("password123")
        assertEquals(ValidationResult.Success, result)
    }
}
