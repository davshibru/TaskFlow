package com.davidshibru.taskflow.data.accounts.exceptions

import com.davidshibru.taskflow.core.essentials.exception.base.AbstractAppException

class LoginIsNotAvailableDataException(
    cause: Throwable? = null,
) : AbstractAppException(
    cause = cause,
    message = "Login is not available",
)
