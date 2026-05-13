package com.davidshibru.taskflow.demo

import androidx.compose.runtime.Composable
import com.davidshibru.taskflow.navigation.common.NavigationHost

@Composable
fun ProvideDemoNavigation(
    navigator: DemoNavigator,
    startDestination: DemoRoute,
    builder: DemoNavGraphBuilder.() -> Unit,
) {
    NavigationHost(
        navigator = navigator,
        startDestination = startDestination,
        navGraphBuilder = builder,
    )
}
