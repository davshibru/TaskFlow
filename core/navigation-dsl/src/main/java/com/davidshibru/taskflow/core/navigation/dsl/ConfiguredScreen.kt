package com.davidshibru.taskflow.core.navigation.dsl

interface ConfiguredScreen {
    val toolbar: ScreenToolbar

    data object Empty : ConfiguredScreen {
        override val toolbar = ScreenToolbar.Hidden
    }
}