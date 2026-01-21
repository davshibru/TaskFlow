package com.davidshibru.taskflow.core.essentials.resources

interface CoreStringProvider : StringProvider {
    val unknownErrorMessage: String
    val connectionErrorMessage: String
    fun backendErrorMessage(code: Int, backendMessage: String) : String
}