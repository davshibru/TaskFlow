package com.davidshibru.taskflow.feature.signup.presentation

import com.davidshibru.taskflow.feature.signup.domain.entities.InputField
import com.davidshibru.taskflow.feature.signup.domain.entities.NewAccount

sealed interface SignUpAction {

    data class SignUp(
        val account: NewAccount,
    ): SignUpAction

    data class Validate(
        val account: NewAccount,
    ): SignUpAction

    data class ClearError(
        val field: InputField<*>,
    ): SignUpAction

    data class EnableErrorMessages(
        val field: InputField<*>,
    ): SignUpAction

}