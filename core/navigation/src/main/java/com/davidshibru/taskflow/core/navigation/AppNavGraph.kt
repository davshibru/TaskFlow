package com.davidshibru.taskflow.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.davidshibru.taskflow.feature.init.presentation.InitScreen
import com.davidshibru.taskflow.features.signin.presentation.SignInScreen

fun NavGraphBuilder.buildAppNavGraph() {
    composable<InitRoute> { InitScreen() }
    composable<SignInRoute> { SignInScreen() }
}