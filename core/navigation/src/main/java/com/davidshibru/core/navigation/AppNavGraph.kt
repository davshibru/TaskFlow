package com.davidshibru.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun AppNavGraph(
    navController: NavHostController,
    startRoute: String,
    entries: Map<String, @JvmSuppressWildcards FeatureEntry>
) {
    NavHost(
        navController = navController,
        startDestination = startRoute
    ) {
        entries.values.forEach { entry ->
            entry.register(this, navController)
        }
    }
}