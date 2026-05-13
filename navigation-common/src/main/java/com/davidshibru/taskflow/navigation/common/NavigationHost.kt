package com.davidshibru.taskflow.navigation.common

import androidx.compose.foundation.background
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.davidshibru.taskflow.core.navigation.dsl.BaseRoute

@Suppress("UNCHECKED_CAST")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> NavigationHost(
    modifier: Modifier = Modifier,
    navigator: Navigator<T>,
    startDestination: T,
    navGraphBuilder: NavGraphBuilder<T>.() -> Unit,
) where T : NavKey, T : BaseRoute {
    val backStack = rememberNavBackStack(startDestination) as NavBackStack<T>

    NavigationEffects(navigationChannel = navigator, backStack = backStack)

    Surface(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
    ) {
        NavDisplay(
            backStack = backStack,
            modifier = modifier,
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator(),
            ),
            entryProvider = entryProvider {
                NavGraphBuilderImpl(
                    origin = this,
                    backStack = backStack,
                ).apply(navGraphBuilder)
            },
        )
    }
}
