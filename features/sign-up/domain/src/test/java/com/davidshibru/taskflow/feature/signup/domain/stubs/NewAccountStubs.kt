package com.davidshibru.taskflow.feature.signup.domain.stubs

import com.davidshibru.taskflow.feature.signup.domain.entities.NewAccount

fun createNewAccount(
    login: String = "test-login",
    password: String = "12345678",
    repeatPassword: String = "12345678",
    firstName: String = "David",
    lastName: String = "Shibru",
    age: Int = 25,
) = NewAccount(
    login = login,
    password = password,
    repeatPassword = repeatPassword,
    firstName = firstName,
    lastName = lastName,
    age = age,
)