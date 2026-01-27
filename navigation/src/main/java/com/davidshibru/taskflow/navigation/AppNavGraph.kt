package com.davidshibru.taskflow.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.davidshibru.taskflow.feature.init.presentation.initScreen
import com.davidshibru.taskflow.feature.signin.presentation.signInScreen
import com.davidshibru.taskflow.navigation.base.ExtendedNavGraphBuilder
import com.davidshibru.taskflow.navigation.base.composable

fun ExtendedNavGraphBuilder.buildAppNavGraph() {
    composable<InitRoute>{ initScreen() }
    composable<SignInRoute> { signInScreen() }
}