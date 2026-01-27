package com.davidshibru.taskflow.core.navigation.dsl

sealed class ScreenToolbar {

    data object Hidden: ScreenToolbar()

    data class Default(
        val title: String,
    ): ScreenToolbar()
}