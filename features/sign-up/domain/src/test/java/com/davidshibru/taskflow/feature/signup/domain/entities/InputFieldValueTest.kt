package com.davidshibru.taskflow.feature.signup.domain.entities

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Test

class InputFieldValueTest {

    @Test
    fun `validate calls inputField validate with value`() {
        // given
        // We use a concrete implementation to avoid MockK's generic ambiguity 
        // that causes ClassCastException during recording.
        val inputField = InputField.Login
        val value = "test value"
        val inputFieldValue = InputFieldValue(inputField, value)

        // when
        val actualResult = inputFieldValue.validate()

        // then
        // Since we are using the real object, we verify its specific validation logic
        val expectedResult = inputField.validate(value)
        assertEquals(expectedResult, actualResult)
    }
}
