package com.davidshibru.taskflow.core.navigation.routers

import com.davidshibru.taskflow.core.navigation.SignInRoute
import com.davidshibru.taskflow.core.navigation.base.AppRouter
import com.davidshibru.taskflow.feature.init.presentation.InitRouter
import javax.inject.Inject

class InitRouterImpl @Inject constructor(
    private val appRouter: AppRouter,
) : InitRouter {

    override fun launchSignIn() {
        appRouter.restart(SignInRoute)
    }
}