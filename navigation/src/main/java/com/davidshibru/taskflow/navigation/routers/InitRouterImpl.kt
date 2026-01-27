package com.davidshibru.taskflow.navigation.routers

import com.davidshibru.taskflow.feature.init.presentation.InitRouter
import com.davidshibru.taskflow.navigation.SignInRoute
import com.davidshibru.taskflow.navigation.base.AppRouter
import javax.inject.Inject

class InitRouterImpl @Inject constructor(
    private val appRouter: AppRouter,
) : InitRouter{
    override fun launchSignInScreen() {
        appRouter.restart(SignInRoute)
    }
}