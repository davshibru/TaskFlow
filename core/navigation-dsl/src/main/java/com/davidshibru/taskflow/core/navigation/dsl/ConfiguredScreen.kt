package com.davidshibru.taskflow.core.navigation.dsl

interface ConfiguredScreen {
    val toolbar: ScreenToolbar

    val navigationBar: ScreenNavigationBar

    val backHandler: ScreenBackHandler
}