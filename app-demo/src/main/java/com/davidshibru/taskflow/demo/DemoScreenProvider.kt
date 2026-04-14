package com.davidshibru.taskflow.demo

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.davidshibru.taskflow.core.navigation.dsl.ConfiguredScreen
import com.davidshibru.taskflow.core.essentials.logger.Logger
import com.davidshibru.taskflow.core.navigation.dsl.ScreenScope
import com.davidshibru.taskflow.core.navigation.dsl.ScreenToolbar
import kotlin.reflect.KClass

@Composable
fun ProvideDemoScreen(
    builder: ScreenScope.() -> Unit
) {
    val context = LocalContext.current

    val screenScope = remember(context) {
        object : ScreenScope {
            override val context: Context = context
            override var toolbar: ScreenToolbar by mutableStateOf(ScreenToolbar.Hidden)

            private var screenContent: @Composable () -> Unit = {}

            override fun content(block: @Composable (() -> Unit)) {
                this.screenContent = block
            }

            @Composable
            fun Render() {
                Scaffold(
                    topBar = {
                        val currentToolbar = toolbar
                        if (currentToolbar is ScreenToolbar.Default) {
                            DemoAppToolBar(
                                toolbar = currentToolbar,
                                showBackButton = false,
                                onBackPressed = {
                                    Logger.d("🔙 Нажата кнопка Назад в Тулбаре")
                                }
                            )
                        }
                    }
                ) { paddingValues ->
                    Box(modifier = Modifier.padding(paddingValues)) {
                        screenContent()
                    }
                }
            }
        }
    }

    screenScope.builder()

    screenScope.Render()
}

@Composable
fun ProvideDemoNavigation(
    navigator: DemoNavigator,
    startDestination: DemoRoute,
    builder: DemoNavGraphBuilder.() -> Unit,
) {
    val context = LocalContext.current
    val navStore = remember(context) { DemoNavStore(context) }
    val navGraphBuilder = remember(navStore) { DemoNavGraphBuilderImpl(navStore) }

    navGraphBuilder.builder()

    LaunchedEffect(navigator, startDestination) {
        navigator.setStartDestination(startDestination)
    }

    val backStack by navigator.backStack.collectAsState()
    val currentEntry = backStack.lastOrNull()
    val showBackButton = backStack.size > 1

    LaunchedEffect(backStack) {
        navStore.onBackStackChanged(backStack)
    }

    BackHandler(enabled = showBackButton) {
        navigator.goBack()
    }

    Scaffold(
        topBar = {
            val toolbar = navStore.screen.toolbar
            if (toolbar is ScreenToolbar.Default) {
                DemoAppToolBar(
                    toolbar = toolbar,
                    showBackButton = showBackButton,
                    onBackPressed = navigator::goBack,
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (currentEntry != null) {
                navStore.Content(currentEntry)
            }
        }
    }
}

private class DemoNavGraphBuilderImpl(
    private val navStore: DemoNavStore,
) : DemoNavGraphBuilder {

    override fun <T : DemoRoute> composable(
        routeClass: KClass<T>,
        content: ScreenScope.(T) -> Unit,
    ) {
        navStore.registerConfiguration(routeClass, content)
    }
}

private class DemoNavStore(
    private val context: Context,
) {

    private val configurations = mutableMapOf<KClass<out DemoRoute>, Configuration<*>>()
    private var screens = mutableMapOf<String, Screen>()
    var screen: ConfiguredScreen by mutableStateOf(ConfiguredScreen.Empty)
        private set

    fun onBackStackChanged(backStack: List<DemoBackStackEntry>) {
        screens = backStack.associateBy(DemoBackStackEntry::id)
            .mapValues { (_, entry) ->
                screens[entry.id] ?: createScreen(entry.route)
            }
            .toMutableMap()

        screen = backStack
            .lastOrNull()
            ?.let { entry -> screens[entry.id] }
            ?: ConfiguredScreen.Empty
    }

    fun <T : DemoRoute> registerConfiguration(
        routeClass: KClass<T>,
        content: ScreenScope.(T) -> Unit,
    ) {
        configurations[routeClass] = Configuration(content)
    }

    @Composable
    fun Content(entry: DemoBackStackEntry) {
        val screen = screens.getOrPut(entry.id) {
            createScreen(entry.route)
        }

        screen.ScreenContent()
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T : DemoRoute> createScreen(route: T): Screen {
        val screen = Screen(context)
        val configuration = requireNotNull(configurations[route::class]) {
            "Demo route is not registered: ${route::class.qualifiedName}"
        } as Configuration<T>

        configuration.applyTo(screen, route)
        return screen
    }

    private class Configuration<T : DemoRoute>(
        private val content: ScreenScope.(T) -> Unit,
    ) {
        fun applyTo(screenScope: ScreenScope, route: T) {
            screenScope.content(route)
        }
    }

    private class Screen(
        override val context: Context,
    ) : ScreenScope {
        override var toolbar: ScreenToolbar by mutableStateOf(ScreenToolbar.Hidden)

        private var content: @Composable () -> Unit by mutableStateOf({})

        override fun content(block: @Composable () -> Unit) {
            content = block
        }

        @Composable
        fun ScreenContent() {
            content()
        }
    }
}
