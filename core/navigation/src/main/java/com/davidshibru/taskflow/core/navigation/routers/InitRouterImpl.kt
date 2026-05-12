package com.davidshibru.taskflow.core.navigation.routers

import com.davidshibru.taskflow.core.navigation.ChatsRoute
import com.davidshibru.taskflow.core.navigation.SignInRoute
import com.davidshibru.taskflow.core.navigation.base.AppNavigator
import com.davidshibru.taskflow.core.navigation.base.launchAuthFlow
import com.davidshibru.taskflow.core.navigation.base.launchMainFlow
import com.davidshibru.taskflow.feature.init.presentation.InitRouter
import javax.inject.Inject

class InitRouterImpl @Inject constructor(
    private val appNavigator: AppNavigator,
) : InitRouter {

    override fun launchAuthFlow() {
        appNavigator.launchAuthFlow()
    }

    override fun launchMainFlow() {
        appNavigator.launchMainFlow()
    }
}