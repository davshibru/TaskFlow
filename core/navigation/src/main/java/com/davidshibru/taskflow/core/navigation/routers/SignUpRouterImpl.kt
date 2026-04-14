package com.davidshibru.taskflow.core.navigation.routers

import com.davidshibru.taskflow.core.navigation.CongratsRoute
import com.davidshibru.taskflow.core.navigation.base.AppNavigator
import com.davidshibru.taskflow.feature.signup.presentation.SignUpRouter
import javax.inject.Inject

class SignUpRouterImpl @Inject constructor(
    private val appNavigator: AppNavigator,
) : SignUpRouter {
    override fun launchCongrats() {
        appNavigator.replace(CongratsRoute)
    }

    override fun goBackToSignIn() {
        appNavigator.goBack()
    }
}