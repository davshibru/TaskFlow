package com.davidshibru.taskflow.core.navigation.base

import com.davidshibru.taskflow.core.navigation.ChatsRoute
import com.davidshibru.taskflow.core.navigation.MainRoute
import com.davidshibru.taskflow.core.navigation.Route
import com.davidshibru.taskflow.core.navigation.SignInRoute
import com.davidshibru.taskflow.navigation.common.Navigator

interface AppNavigator : Navigator<Route>

fun AppNavigator.launchMainFlow() = restart(MainRoute)
fun AppNavigator.launchAuthFlow() = restart(SignInRoute)
