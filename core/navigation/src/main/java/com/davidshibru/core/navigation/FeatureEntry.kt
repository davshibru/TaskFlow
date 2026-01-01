package com.davidshibru.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

interface FeatureEntry {
    val route: String

    fun register(
        builder: NavGraphBuilder,
        navController: NavHostController
    )
}