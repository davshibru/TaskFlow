package com.davidshibru.taskflow

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.davidshibru.auth.api.AuthEntry
import com.davidshibru.core.navigation.FeatureEntryRegistry
import com.davidshibru.splash.api.SplashEntry
import javax.inject.Inject

class Navigation @Inject constructor(
    private val registry: FeatureEntryRegistry
) {

    @Composable
    fun Content(
        paddingValues: PaddingValues = PaddingValues(),
    ) {
        val navController = rememberNavController()

        val splash = registry.get(SplashEntry::class)
        val auth = registry.get(AuthEntry::class)

        NavHost(
            modifier = Modifier.padding(paddingValues),
            navController = navController,
            startDestination = splash.featureRoute
        ) {
            with(splash) {
                registry(navController)
            }

            with(auth) {
                registry(navController)
            }
        }
    }
}