package com.davidshibru.taskflow.feature.signup.domain.entities

data class NewAccount(
    val login: String,
    val password: String,
    val repeatPassword: String,
    val firstName: String,
    val lastName: String,
    val age: Int,
) {

    companion object {
        const val EMPTY_AGE = -1
    }
}

fun NewAccount.toFieldValues(): List<InputFieldValue<*>> {
    return listOf(
        InputFieldValue(InputField.Login, login),
        InputFieldValue(InputField.Password, password),
        InputFieldValue(InputField.RepeatPassword, repeatPassword),
        InputFieldValue(InputField.FirstName, firstName),
        InputFieldValue(InputField.LastName, lastName),
        InputFieldValue(InputField.Age, age),
    )
}