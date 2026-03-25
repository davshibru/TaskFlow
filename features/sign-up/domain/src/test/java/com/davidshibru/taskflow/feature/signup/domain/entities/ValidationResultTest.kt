package com.davidshibru.taskflow.feature.signup.domain.entities

import com.davidshibru.taskflow.feature.signup.domain.entities.ValidationResult.Companion.combine
import com.davidshibru.taskflow.feature.signup.domain.exceptions.PasswordMismatchException
import com.davidshibru.taskflow.feature.signup.domain.exceptions.TooShortValueException
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ValidationResultTest {

    @Test
    fun `Success plus exception returns Failure with that exception`() {
        val exception = PasswordMismatchException()
        val result = ValidationResult.Success + exception
        
        assertTrue(result is ValidationResult.Failure)
        assertEquals(listOf(exception), (result as ValidationResult.Failure).exceptions)
    }

    @Test
    fun `Failure plus exception returns Failure with appended exception`() {
        val ex1 = PasswordMismatchException()
        val ex2 = TooShortValueException(InputField.Login, "abc")
        val failure = ValidationResult.Failure(ex1)
        
        val result = failure + ex2
        
        assertTrue(result is ValidationResult.Failure)
        assertEquals(listOf(ex1, ex2), (result as ValidationResult.Failure).exceptions)
    }

    @Test
    fun `combine empty list returns Success`() {
        val results = emptyList<ValidationResult>()
        assertEquals(ValidationResult.Success, results.combine())
    }

    @Test
    fun `combine all Success results returns Success`() {
        val results = listOf(ValidationResult.Success, ValidationResult.Success)
        assertEquals(ValidationResult.Success, results.combine())
    }

    @Test
    fun `combine Success and Failure results returns Failure with all exceptions`() {
        val ex1 = PasswordMismatchException()
        val ex2 = TooShortValueException(InputField.Login, "abc")
        val results = listOf(
            ValidationResult.Success,
            ValidationResult.Failure(ex1),
            ValidationResult.Failure(ex2)
        )
        
        val combined = results.combine()
        
        assertTrue(combined is ValidationResult.Failure)
        assertEquals(listOf(ex1, ex2), (combined as ValidationResult.Failure).exceptions)
    }
}
