package com.davidshibru.taskflow.features.signin.domain.entities

import com.davidshibru.taskflow.features.signin.domain.exceptions.EmptyFieldException

data class Credentials(
    val login: String,
    val password: String,
)

/**
 * @throws EmptyFieldException
 * */
internal fun Credentials.validate() {
    if (login.isBlank()) throw EmptyFieldException(InputField.Login)
    if (password.isBlank()) throw EmptyFieldException(InputField.Password)
}