package com.davidshibru.taskflow.core.navigation

import androidx.compose.foundation.background
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.davidshibru.taskflow.core.navigation.base.AppNavigator
import com.davidshibru.taskflow.core.navigation.base.ExtendedNavGraphBuilder
import com.davidshibru.taskflow.core.navigation.base.impl.ComposeDialogs
import com.davidshibru.taskflow.core.navigation.base.impl.ExtendedNavGraphBuilderImpl

@Suppress("UNCHECKED_CAST")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    appNavigator: AppNavigator,
    startDestination: Route = InitRoute,
    navGraphBuilder: ExtendedNavGraphBuilder.() -> Unit = {},
) {

    val backStack = rememberNavBackStack(startDestination)
            as NavBackStack<Route>

    NavigationEffects(navigationChannel = appNavigator, backStack = backStack)
    val dialogs = remember { ComposeDialogs() }

    Surface(
        modifier = Modifier.background(MaterialTheme.colorScheme.background)
    ) {
        NavDisplay(
            backStack = backStack,
            modifier = modifier,
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator(),
            ),
            entryProvider = entryProvider {
                ExtendedNavGraphBuilderImpl(origin = this, backStack = backStack).apply {
                    buildAppNavGraph()
                    navGraphBuilder()
                }
            }
        )

        dialogs.Renderer()
    }
}