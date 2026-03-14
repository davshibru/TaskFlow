package com.davidshibru.taskflow.core.navigation

import com.davidshibru.taskflow.core.navigation.base.ExtendedNavGraphBuilder
import com.davidshibru.taskflow.core.navigation.base.composable
import com.davidshibru.taskflow.feature.init.presentation.initScreen
import com.davidshibru.taskflow.features.signin.presentation.signInScreen

fun ExtendedNavGraphBuilder.buildAppNavGraph() {
    composable<InitRoute> { initScreen() }
    composable<SignInRoute> { signInScreen() }
}