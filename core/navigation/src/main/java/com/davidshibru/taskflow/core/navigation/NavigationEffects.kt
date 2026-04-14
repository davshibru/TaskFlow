package com.davidshibru.taskflow.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import com.davidshibru.taskflow.core.navigation.base.AppNavigator
import com.davidshibru.taskflow.core.navigation.base.NavigationIntent
import com.davidshibru.taskflow.core.navigation.base.impl.getRouteClass

@Composable
fun NavigationEffects(
    navigationChannel: AppNavigator,
    navHostController: NavHostController,
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(navigationChannel, lifecycleOwner) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            navigationChannel.navigationEvents.collect { intent ->
                when (intent) {
                    is NavigationIntent.NavigateTo -> {
                        navHostController.navigate(intent.route)
                    }
                    is NavigationIntent.Restart -> {
                        navHostController.navigate(intent.route) {
                            popUpTo(navHostController.graph.id) { inclusive = true }
                        }
                    }
                    is NavigationIntent.Replace -> {
                        navHostController.currentBackStackEntry?.getRouteClass()?.let { currentRouteClass ->
                            navHostController.navigate(intent.route) {
                                popUpTo(currentRouteClass) {
                                    inclusive = true
                                }
                            }
                        }
                    }
                    is NavigationIntent.GoBack -> {
                        navHostController.navigateUp()
                    }
                }
            }
        }
    }
}