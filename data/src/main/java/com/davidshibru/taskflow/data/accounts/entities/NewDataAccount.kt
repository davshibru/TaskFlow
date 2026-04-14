package com.davidshibru.taskflow.data.accounts.entities

data class NewDataAccount(
    val login: String,
    val password: String,
    val firstName: String,
    val lastName: String,
) {
    companion object {
        fun NewDataAccount.displayName(): String = "$firstName $lastName".trim()
    }
}