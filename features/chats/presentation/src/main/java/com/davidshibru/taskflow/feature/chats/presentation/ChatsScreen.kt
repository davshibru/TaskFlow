package com.davidshibru.taskflow.feature.chats.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.davidshibru.taskflow.core.essentials.container.Container
import com.davidshibru.taskflow.core.navigation.dsl.ScreenScope
import com.davidshibru.taskflow.core.navigation.dsl.ScreenToolbar
import com.davidshibru.taskflow.core.theme.components.ContainerView

fun ScreenScope.chatsScreen() {
    toolbar = ScreenToolbar.Hidden

    content {
        val viewModel: ChatsViewModel = hiltViewModel()
        val container: Container<ChatsViewModel.State> by viewModel.stateFlow.collectAsState()

        ContainerView(
            container = container,
        ) { state ->
            ChatsContent(state)
        }
    }
}

@Composable
private fun ChatsContent(state: ChatsViewModel.State) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = state.title)
    }
}
