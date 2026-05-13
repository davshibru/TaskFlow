package com.davidshibru.taskflow.navigation.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.davidshibru.taskflow.core.navigation.dsl.ScreenNavigationBar
import com.davidshibru.taskflow.core.navigation.dsl.ScreenToolbar

@Composable
fun NavigationScreenScaffold(
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
                AppToolBar(
                    toolbar = toolbar,
                    showBackButton = showBackButton,
                    onBackPressed = onBackPressed,
                )
            }
        },
        bottomBar = {
            if (navigationBar is ScreenNavigationBar.Default) {
                AppNavigationBar(navigationBar)
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
