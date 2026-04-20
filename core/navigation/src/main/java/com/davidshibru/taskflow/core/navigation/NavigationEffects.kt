package com.davidshibru.taskflow.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation3.runtime.NavBackStack
import com.davidshibru.taskflow.core.navigation.base.AppNavigator
import com.davidshibru.taskflow.core.navigation.base.NavigationIntent

@Composable
fun NavigationEffects(
    navigationChannel: AppNavigator,
    backStack: NavBackStack<Route>,
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(navigationChannel, lifecycleOwner) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            navigationChannel.navigationEvents.collect { intent ->
                when (intent) {
                    is NavigationIntent.NavigateTo -> {
                        backStack.add(intent.route)
                    }
                    is NavigationIntent.Restart -> {
                        backStack.clear()
                        backStack.add(intent.route)
                    }
                    is NavigationIntent.Replace -> {
                        backStack.removeLastOrNull()
                        backStack.add(intent.route)
                    }
                    is NavigationIntent.GoBack -> {
                        backStack.removeLastOrNull()
                    }
                }
            }
        }
    }
}