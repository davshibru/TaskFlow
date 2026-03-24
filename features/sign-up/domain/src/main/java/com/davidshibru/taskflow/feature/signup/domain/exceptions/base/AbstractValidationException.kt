package com.davidshibru.taskflow.feature.signup.domain.exceptions.base

import com.davidshibru.taskflow.feature.signup.domain.entities.InputField

abstract class AbstractValidationException(
    message: String,
    cause: Throwable? = null,
) : AbstractSignUpAppException(message = message, cause = cause) {

    abstract val inputField: InputField<*>
}