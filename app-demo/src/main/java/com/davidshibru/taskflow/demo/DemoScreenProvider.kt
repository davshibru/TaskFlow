package com.davidshibru.taskflow.demo

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.davidshibru.taskflow.core.essentials.logger.Logger
import com.davidshibru.taskflow.core.navigation.dsl.ScreenScope
import com.davidshibru.taskflow.core.navigation.dsl.ScreenToolbar


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