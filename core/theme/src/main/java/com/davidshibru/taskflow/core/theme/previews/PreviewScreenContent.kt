package com.davidshibru.taskflow.core.theme.previews

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.davidshibru.taskflow.core.theme.material.TaskFlowTheme

@Composable
fun PreviewScreenContent(content: @Composable () -> Unit) {
    TaskFlowTheme { Surface { content() } }
}