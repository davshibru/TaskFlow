package com.davidshibru.taskflow.features.signin.domain.entities

import com.davidshibru.taskflow.features.signin.domain.resources.SignInStringProvider

enum class InputField(val fieldName: SignInStringProvider.() -> String) {
    Login({ loginFieldName }),
    Password({ passwordFieldName }),
}