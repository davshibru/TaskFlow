package com.davidshibru.taskflow.features.signin.domain.resources

import com.davidshibru.taskflow.core.essentials.resources.StringProvider
import com.davidshibru.taskflow.features.signin.domain.entities.InputField

interface SignInStringProvider : StringProvider {
    val loginFieldName: String
    val passwordFieldName: String
    val invalidCredentialsError: String
    fun emptyFieldError(field: InputField): String
}