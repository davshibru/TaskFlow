package com.davidshibru.taskflow.core.navigation.base.impl

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import com.davidshibru.taskflow.core.navigation.AppScreenScaffold
import com.davidshibru.taskflow.core.navigation.Route
import com.davidshibru.taskflow.core.navigation.base.ExtendedNavGraphBuilder
import com.davidshibru.taskflow.core.navigation.dsl.ScreenScope
import kotlin.reflect.KClass

class ExtendedNavGraphBuilderImpl(
    private val backStack: NavBackStack<Route>,
    private val origin: EntryProviderScope<Route>,
) : ExtendedNavGraphBuilder {

    override fun <T : Route> composable(
        routeCLass: KClass<T>,
        content: ScreenScope.(T) -> Unit
    ) {
        origin.addEntryProvider(
            clazz = routeCLass,
            content = { route ->
                val context = LocalContext.current
                val coroutineScope = rememberCoroutineScope()

                val viewModelStoreOwner = requireNotNull(LocalViewModelStoreOwner.current)
                viewModelStoreOwner as HasDefaultViewModelProviderFactory

                val nav3ScreenScope = remember(
                    context, coroutineScope, viewModelStoreOwner, route
                ) {
                    Nav3ScreenScope(
                        context,
                        coroutineScope,
                        viewModelStoreOwner,
                        viewModelStoreOwner
                    ).apply {
                        content(this, route)
                    }
                }
                AppScreenScaffold(
                    toolbar = nav3ScreenScope.toolbar,
                    navigationBar = nav3ScreenScope.navigationBar,
                    onBackPressed = { backStack.removeLastOrNull() },
                    showBackButton = backStack.indexOf(route) != 0,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    nav3ScreenScope.Content()
                }
            }
        )
    }
}