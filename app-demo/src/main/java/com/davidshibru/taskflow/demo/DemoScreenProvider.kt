package com.davidshibru.taskflow.demo

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.HiltViewModelFactory
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.davidshibru.taskflow.core.navigation.dsl.ScreenNavigationBar
import com.davidshibru.taskflow.core.navigation.dsl.ScreenScope
import com.davidshibru.taskflow.core.navigation.dsl.ScreenToolbar
import dagger.hilt.android.lifecycle.withCreationCallback
import kotlinx.coroutines.CoroutineScope
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
@Composable
fun ProvideDemoNavigation(
    navigator: DemoNavigator,
    startDestination: DemoRoute,
    builder: DemoNavGraphBuilder.() -> Unit,
) {
    val backStack = rememberNavBackStack(startDestination) as NavBackStack<DemoRoute>

    DemoNavigationEffects(
        navigationChannel = navigator,
        backStack = backStack,
    )

    Surface(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
    ) {
        NavDisplay(
            backStack = backStack,
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator(),
            ),
            entryProvider = entryProvider {
                DemoNavGraphBuilderImpl(
                    backStack = backStack,
                    origin = this,
                ).apply(builder)
            },
        )
    }
}

@Composable
private fun DemoNavigationEffects(
    navigationChannel: DemoNavigator,
    backStack: NavBackStack<DemoRoute>,
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(navigationChannel, lifecycleOwner) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            navigationChannel.navigationEvents.collect { intent ->
                when (intent) {
                    is DemoNavigationIntent.NavigateTo -> {
                        backStack.add(intent.route)
                    }
                    is DemoNavigationIntent.Restart -> {
                        backStack.clear()
                        backStack.add(intent.route)
                    }
                    is DemoNavigationIntent.Replace -> {
                        backStack.removeLastOrNull()
                        backStack.add(intent.route)
                    }
                    DemoNavigationIntent.GoBack -> {
                        backStack.removeLastOrNull()
                    }
                }
            }
        }
    }
}

private class DemoNavGraphBuilderImpl(
    private val backStack: NavBackStack<DemoRoute>,
    private val origin: EntryProviderScope<DemoRoute>,
) : DemoNavGraphBuilder {

    override fun <T : DemoRoute> composable(
        routeClass: KClass<T>,
        content: ScreenScope.(T) -> Unit,
    ) {
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
                    DemoNav3ScreenScope(
                        context = context,
                        coroutineScope = coroutineScope,
                        viewModelStoreOwner = viewModelStoreOwner,
                        defaultsProvider = viewModelStoreOwner,
                    ).apply {
                        content(this, route)
                    }
                }

                DemoScreenScaffold(
                    toolbar = screenScope.toolbar,
                    navigationBar = screenScope.navigationBar,
                    showBackButton = backStack.indexOf(route) != 0,
                    onBackPressed = { backStack.removeLastOrNull() },
                ) {
                    screenScope.Content()
                }
            },
        )
    }
}

@Composable
private fun DemoScreenScaffold(
    toolbar: ScreenToolbar,
    navigationBar: ScreenNavigationBar,
    showBackButton: Boolean,
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit,
    content: @Composable () -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            if (toolbar is ScreenToolbar.Default) {
                DemoAppToolBar(
                    toolbar = toolbar,
                    showBackButton = showBackButton,
                    onBackPressed = onBackPressed,
                )
            }
        },
        bottomBar = {
            if (navigationBar is ScreenNavigationBar.Default) {
                DemoNavigationBar(
                    navigationBar = navigationBar,
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            content()
        }
    }
}

private class DemoNav3ScreenScope(
    override val context: Context,
    override val coroutineScope: CoroutineScope,
    private val viewModelStoreOwner: ViewModelStoreOwner,
    private val defaultsProvider: HasDefaultViewModelProviderFactory,
) : ScreenScope,
    ViewModelStoreOwner by viewModelStoreOwner,
    HasDefaultViewModelProviderFactory by defaultsProvider {

    override var toolbar: ScreenToolbar by mutableStateOf(ScreenToolbar.Hidden)
    override var navigationBar: ScreenNavigationBar by mutableStateOf(ScreenNavigationBar.Hidden)

    private var content: @Composable () -> Unit by mutableStateOf({})

    override fun content(block: @Composable () -> Unit) {
        content = block
    }

    @Composable
    fun Content() {
        content()
    }

    override fun <T : ViewModel> viewModel(vmClass: KClass<T>): T {
        return getViewModel<T, Nothing>(vmClass)
    }

    override fun <T : ViewModel, F> viewModel(
        vmClass: KClass<T>,
        callback: F.() -> T,
    ): T {
        return getViewModel(vmClass, callback)
    }

    private fun <T : ViewModel, F> getViewModel(
        vmClass: KClass<T>,
        callback: (F.() -> T)? = null,
    ): T {
        val factory = HiltViewModelFactory(context, defaultViewModelProviderFactory)
        val extras = defaultViewModelCreationExtras
        val finalExtras = if (callback == null) {
            extras
        } else {
            extras.withCreationCallback(callback)
        }
        val provider = ViewModelProvider.create(viewModelStoreOwner, factory, finalExtras)
        return provider[vmClass]
    }
}
