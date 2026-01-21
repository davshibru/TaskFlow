package com.davidshibru.taskflow.core.essentials.exceptions

abstract class AppExceptions (
    message: String,
    cause: Throwable? = null,
) : Exception(message, cause)

class UnknownException : AppExceptions("Unknown exception")