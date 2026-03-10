package com.davidshibru.taskflow.core.essentials.exception

abstract class AppException (
    message: String,
    cause: Throwable? = null,
) : Exception(message, cause)

class UnknownException: AppException("Unknown exception")