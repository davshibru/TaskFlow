package com.davidshibru.feature.splash.impl

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.davidshibru.feature.auth.api.AuthEntry
import com.davidshibru.taskflow.feature.splash.api.SplashEntry
import kotlinx.coroutines.delay
import javax.inject.Inject

class SplashEntryImpl @Inject constructor() : SplashEntry() {

    override fun NavGraphBuilder.register(navController: NavHostController) {
        composable(featureRoute) {
            SplashScreen(
                onTimeout = {
                    navController.navigate("auth") {
                        popUpTo(featureRoute) { inclusive = true }
                    }
                }
            )
        }
    }
}

@Composable
private fun SplashScreen(
    onTimeout: () -> Unit
) {
    LaunchedEffect(Unit) {
        delay(1000L)
        onTimeout.invoke()
    }

    Text("Splash...")
}