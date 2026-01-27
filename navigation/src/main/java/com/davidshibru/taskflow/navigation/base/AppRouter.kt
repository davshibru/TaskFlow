package com.davidshibru.taskflow.navigation.base

import com.davidshibru.taskflow.navigation.Routes

interface AppRouter {

    fun launch(route: Routes)

    fun restart(route: Routes)

    fun goBack()

}