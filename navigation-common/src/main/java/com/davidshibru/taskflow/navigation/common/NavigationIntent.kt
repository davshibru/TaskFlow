package com.davidshibru.taskflow.navigation.common

import androidx.navigation3.runtime.NavKey

sealed interface NavigationIntent<out T : NavKey> {
    data class NavigateTo<T : NavKey>(val route: T) : NavigationIntent<T>
    data class Restart<T : NavKey>(val route: T) : NavigationIntent<T>
    data class Replace<T : NavKey>(val route: T) : NavigationIntent<T>
    data object GoBack : NavigationIntent<Nothing>
}
