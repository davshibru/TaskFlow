package com.davidshibru.taskflow.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.davidshibru.taskflow.core.navigation.base.AppNavigator
import com.davidshibru.taskflow.core.navigation.base.ExtendedNavGraphBuilder
import com.davidshibru.taskflow.core.navigation.base.impl.ComposeDialogs
import com.davidshibru.taskflow.navigation.common.NavigationHost

@Suppress("UNCHECKED_CAST")
@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    appNavigator: AppNavigator,
    startDestination: Route = InitRoute,
    navGraphBuilder: ExtendedNavGraphBuilder.() -> Unit = {},
) {
    val dialogs = remember { ComposeDialogs() }

    NavigationHost(
        modifier = modifier,
        navigator = appNavigator,
        startDestination = startDestination,
    ) {
        buildAppNavGraph()
        navGraphBuilder()
    }

    dialogs.Renderer()
}
