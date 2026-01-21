package com.davidshibru.taskflow.core.essentials.exceptions.base

abstract class AppExceptions(
    message: String,
    cause: Throwable? = null,
) : Exception(message, cause)




