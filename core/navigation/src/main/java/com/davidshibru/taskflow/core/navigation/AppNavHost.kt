package com.davidshibru.taskflow.core.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.davidshibru.taskflow.core.navigation.base.AppNavigator

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    appNavigator: AppNavigator,
    startDestination: Route = InitRoute,
    navGraphBuilder: NavGraphBuilder.() -> Unit = {},
) {
    val navController = rememberNavController()

    NavigationEffects(
        navigationChannel = appNavigator,
        navHostController = navController,
    )

    Scaffold(
        modifier = modifier,
    ) { paddingValues ->
        NavHost(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            navController = navController,
            startDestination = startDestination,
        ) {
            buildAppNavGraph()
            navGraphBuilder()
        }
    }
}