package com.davidshibru.taskflow.core.essentials.exception

import com.davidshibru.taskflow.core.essentials.exception.base.AbstractAppException

class UnknownException(
    cause: Throwable
) : AbstractAppException("Unknown exception", cause)