package com.davidshibru.taskflow.core.essentials.exception

interface ExceptionHandler {
    fun handleException(exception: Exception)
}