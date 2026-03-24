package com.davidshibru.taskflow.feature.signup.domain.resources

import com.davidshibru.taskflow.core.essentials.resources.StringProvider
import com.davidshibru.taskflow.feature.signup.domain.entities.InputField

interface SignUpStringProvider : StringProvider {

    val loginField: String
    val passwordField: String
    val repeatPasswordField: String
    val firstNameField: String
    val lastNameField: String
    val ageField: String

    val loginAlreadyExistsError: String
    val passwordMismatchError: String

    fun emptyFieldError(field: InputField.Text): String
    fun emptyFieldError(field: InputField.Number): String
    fun tooLongValueError(field: InputField.Text): String
    fun tooShortValueError(field: InputField.Text): String
    fun invalidRangeError(field: InputField.Number): String
}
