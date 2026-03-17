package com.davidshibru.taskflow.data.session.entities

sealed class Token {

    data object Empty : Token()
}