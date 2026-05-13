package com.davidshibru.taskflow.navigation.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

@Composable
fun <T : NavKey> NavigationEffects(
    navigationChannel: Navigator<T>,
    backStack: NavBackStack<T>,
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

                    NavigationIntent.GoBack -> {
                        backStack.removeLastOrNull()
                    }
                }
            }
        }
    }
}
