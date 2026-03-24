package com.davidshibru.taskflow.feature.signup.domain.entities

data class InputFieldValue<T: Any>(
    val inputField: InputField<T>,
    val value: T,
) {
    fun validate(): ValidationResult {
        return inputField.validate(value)
    }
}