package com.davidshibru.taskflow.core.navigation

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.davidshibru.taskflow.core.navigation.base.AppNavigator
import com.davidshibru.taskflow.core.navigation.base.ExtendedNavGraphBuilder
import com.davidshibru.taskflow.core.navigation.base.ExtendedNavGraphBuilderImpl
import com.davidshibru.taskflow.core.navigation.base.ExtendedNavStoreImpl
import com.davidshibru.taskflow.core.navigation.dsl.ScreenToolbar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    appNavigator: AppNavigator,
    startDestination: Route = InitRoute,
    navGraphBuilder: ExtendedNavGraphBuilder.() -> Unit = {},
) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val navStore = remember { ExtendedNavStoreImpl(context) }

    NavigationEffects(
        navigationChannel = appNavigator,
        navHostController = navController,
    )
    var showBackButton by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val navigator = navController.navigatorProvider
            .getNavigator(ComposeNavigator::class.java)

        navigator.backStack.collect { backStack ->
            navStore.onBackStackChanged(backStack)
            showBackButton = backStack.size > 1
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            val toolbar = navStore.screen.toolbar
            if (toolbar is ScreenToolbar.Default) {
                AppToolBar(
                    toolbar = toolbar,
                    showBackButton = showBackButton,
                    onBackPressed = {
                        navController.navigateUp()
                    }
                )
            }
        }
    ) { paddingValues ->

        val topPadding = animateDpAsState(paddingValues.calculateTopPadding())

        NavHost(
            modifier = Modifier
                .fillMaxSize()
                .padding(topPadding.value),
            navController = navController,
            startDestination = startDestination,
        ) {
            with(ExtendedNavGraphBuilderImpl(this, navStore)) {
                buildAppNavGraph()
                navGraphBuilder()
            }
        }
    }
}