package com.davidshibru.taskflow.features.main.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import com.davidshibru.taskflow.core.navigation.dsl.NavigationButton
import com.davidshibru.taskflow.core.navigation.dsl.ScreenBackHandler
import com.davidshibru.taskflow.core.navigation.dsl.ScreenNavigationBar
import com.davidshibru.taskflow.core.navigation.dsl.ScreenScope
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

fun ScreenScope.mainScreen(
    vararg tabs: Tab,
) {
    require(tabs.isNotEmpty()) { "mainScreen requires at least one tab" }

    val currentIndexFlow = savedStateHandle
        .getMutableStateFlow("currentIndex", 0)

    currentIndexFlow
        .onEach { currentIndex ->
            val selectedIndex = currentIndex.coerceIn(tabs.indices)
            if (selectedIndex != currentIndex) {
                currentIndexFlow.value = selectedIndex
                return@onEach
            }

            val buttons = tabs.mapIndexed { index, tab ->
                NavigationButton(
                    icon = tab.icon,
                    label = tab.run { context.label() },
                    isSelected = selectedIndex == index,
                    onClick = {
                        currentIndexFlow.value = index
                    }
                )
            }

            navigationBar = ScreenNavigationBar.Default(buttons = buttons.toImmutableList())

            backHandler = if (selectedIndex == 0) {
                ScreenBackHandler.Default
            } else {
                ScreenBackHandler.Custom {
                    currentIndexFlow.value = 0
                }
            }
            val currentTab = tabs[selectedIndex]
            val tabScope = SavableScreenScope(
                index = selectedIndex,
                origin = this,
            )
            applyConfiguration(
                route = currentTab,
                scope = tabScope,
            )
        }
        .launchIn(coroutineScope)
}

private class SavableScreenScope(
    val index: Int,
    val origin: ScreenScope,
) : ScreenScope by origin {
    override fun content(block: @Composable (() -> Unit)) {
        origin.content {
            val stateHolder = rememberSaveableStateHolder()
            stateHolder.SaveableStateProvider(index) {
                block()
            }
        }
    }
}
