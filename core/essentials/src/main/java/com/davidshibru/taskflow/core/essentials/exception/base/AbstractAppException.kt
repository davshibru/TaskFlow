package com.davidshibru.taskflow.core.essentials.exception.base

abstract class AbstractAppException(
    message: String,
    cause: Throwable? = null,
) : Exception(message, cause)