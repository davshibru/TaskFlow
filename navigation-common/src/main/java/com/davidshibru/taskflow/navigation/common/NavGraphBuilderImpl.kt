package com.davidshibru.taskflow.navigation.common

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.davidshibru.taskflow.core.navigation.dsl.BaseRoute
import com.davidshibru.taskflow.core.navigation.dsl.ScreenBackHandler
import com.davidshibru.taskflow.core.navigation.dsl.ScreenScope
import kotlin.reflect.KClass

internal class NavGraphBuilderImpl<T>(
    private val backStack: NavBackStack<T>,
    private val origin: EntryProviderScope<T>,
) : NavGraphBuilder<T> where T : NavKey, T : BaseRoute {

    private val screenConfigurationStore = ScreenConfigurationStore()

    override fun <R : T> composable(
        routeClass: KClass<R>,
        content: ScreenScope.(R) -> Unit,
    ) {
        screenConfigurationStore.registerConfiguration(routeClass, content)

        origin.addEntryProvider(
            clazz = routeClass,
            content = { route ->
                val context = LocalContext.current
                val coroutineScope = rememberCoroutineScope()

                val viewModelStoreOwner = requireNotNull(LocalViewModelStoreOwner.current)
                viewModelStoreOwner as HasDefaultViewModelProviderFactory

                val screenScope = remember(
                    context,
                    coroutineScope,
                    viewModelStoreOwner,
                    route,
                ) {
                    Nav3ScreenScope(
                        context = context,
                        coroutineScope = coroutineScope,
                        screenConfigurationStore = screenConfigurationStore,
                        viewModelStoreOwner = viewModelStoreOwner,
                        defaultsProvider = viewModelStoreOwner,
                    ).apply {
                        content(this, route)
                    }
                }

                val defaultBackPressed: () -> Unit = {
                    if (backStack.size > 1) {
                        backStack.removeLastOrNull()
                    }
                }

                val onBackPressed: () -> Unit = when (val backHandler = screenScope.backHandler) {
                    ScreenBackHandler.Default -> defaultBackPressed
                    is ScreenBackHandler.Custom -> backHandler.onBackPressed
                }

                BackHandler(
                    enabled = screenScope.backHandler is ScreenBackHandler.Custom,
                    onBack = onBackPressed,
                )

                NavigationScreenScaffold(
                    toolbar = screenScope.toolbar,
                    navigationBar = screenScope.navigationBar,
                    onBackPressed = onBackPressed,
                    showBackButton = backStack.size > 1,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    screenScope.Content()
                }
            },
        )
    }
}
