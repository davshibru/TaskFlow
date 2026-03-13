package com.davidshibru.taskflow.core.navigation.base

import com.davidshibru.taskflow.core.navigation.Route

sealed interface NavigationIntent {
    data class NavigateTo(val route: Route) : NavigationIntent
    data class Restart(val route: Route) : NavigationIntent
    data object GoBack : NavigationIntent
}