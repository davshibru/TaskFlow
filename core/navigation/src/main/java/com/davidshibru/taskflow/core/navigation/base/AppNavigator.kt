package com.davidshibru.taskflow.core.navigation.base

import com.davidshibru.taskflow.core.navigation.ChatsRoute
import com.davidshibru.taskflow.core.navigation.Route
import com.davidshibru.taskflow.core.navigation.SignInRoute
import kotlinx.coroutines.flow.Flow

interface AppNavigator {
    val navigationEvents: Flow<NavigationIntent>

    fun launch(route: Route)
    fun restart(route: Route)
    fun replace(route: Route)
    fun goBack()
}

fun AppNavigator.launchMainFlow() = restart(ChatsRoute)
fun AppNavigator.launchAuthFlow() = restart(SignInRoute)
