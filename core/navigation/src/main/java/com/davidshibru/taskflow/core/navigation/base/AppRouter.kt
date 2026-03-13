package com.davidshibru.taskflow.core.navigation.base

import com.davidshibru.taskflow.core.navigation.Route

interface AppRouter {

    fun launch(route: Route)

    fun restart(route: Route)

    fun goBack()
}