package com.davidshibru.taskflow.core.essentials.resources

interface CoreStringProvider : StringProvider {
    val connectionErrorMessage: String
    val unknownErrorMessage: String
    val authErrorMessage: String
    val invalidBackendResponseMessage: String
    fun backendErrorMessage(code: Int, backendMessage: String): String

    val deleteAction: String
    val cancelAction: String
}