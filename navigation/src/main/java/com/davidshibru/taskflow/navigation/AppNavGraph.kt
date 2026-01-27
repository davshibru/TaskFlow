package com.davidshibru.taskflow.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.davidshibru.taskflow.feature.init.presentation.InitScreen
import com.davidshibru.taskflow.feature.signin.presentation.SignInScreen

fun NavGraphBuilder.buildAppNavGraph() {
    composable(InitRoute.feature){ InitScreen() }
    composable(SignInRoute.feature) { SignInScreen() }
}