package com.davidshibru.splash.impl

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.davidshibru.splash.api.SplashEntry
import kotlinx.coroutines.delay
import javax.inject.Inject

class SplashEntryImpl @Inject constructor() : SplashEntry() {

    override fun NavGraphBuilder.registry(navController: NavHostController) {
        composable(featureRoute) {
            Box(modifier = Modifier.fillMaxSize()) {
                Text("Splash screen")
            }

            LaunchedEffect(Unit) {
                delay(5000)
                navController.navigate("auth")
            }
        }
    }
}