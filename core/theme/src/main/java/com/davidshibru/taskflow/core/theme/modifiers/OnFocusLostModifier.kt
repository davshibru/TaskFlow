package com.davidshibru.taskflow.core.theme.modifiers

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.onFocusChanged
import kotlinx.coroutines.flow.scan

fun Modifier.onFocusLost(onFocusLost: () -> Unit): Modifier {
    return composed {
        val isFocusedState = remember { mutableStateOf(false) }
        val onFocusLostState = rememberUpdatedState(onFocusLost)
        LaunchedEffect(Unit) {
            snapshotFlow { isFocusedState.value }
                .scan(FocusHistory()) { history, next->
                    FocusHistory(
                        previous = history.current,
                        current = next,
                    )
                }
                .collect { (previous, current) ->
                    if (previous && !current) {
                        onFocusLostState.value()
                    }
                }
        }
        onFocusChanged { focusState ->
            isFocusedState.value = focusState.isFocused
        }
    }
}

private data class FocusHistory(
    val previous: Boolean = false,
    val current: Boolean = false,
)