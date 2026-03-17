package com.davidshibru.taskflow.demo

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
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
                screenContent()
            }
        }
    }

    screenScope.builder()

    screenScope.Render()
}