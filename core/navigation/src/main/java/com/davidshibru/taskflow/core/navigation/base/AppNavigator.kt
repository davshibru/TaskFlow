package com.davidshibru.taskflow.core.navigation.base

import com.davidshibru.taskflow.core.navigation.Route
import kotlinx.coroutines.flow.Flow

interface AppNavigator {
    val navigationEvents: Flow<NavigationIntent>

    fun launch(route: Route)
    fun restart(route: Route)
    fun replace(route: Route)
    fun goBack()
}
