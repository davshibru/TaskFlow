package com.davidshibru.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

interface FeatureEntry {

    val featureRoute: String

    fun NavGraphBuilder.registry(
        navController: NavHostController,
    )
}