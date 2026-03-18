package com.davidshibru.taskflow.data.accounts.exceptions

import com.davidshibru.taskflow.core.essentials.exception.base.AbstractAppException

class InvalidCredentialsDataException(
    cause: Throwable? = null,
) : AbstractAppException("Invalid login and/or password", cause) {
}