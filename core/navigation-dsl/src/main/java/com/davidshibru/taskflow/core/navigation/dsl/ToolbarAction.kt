package com.davidshibru.taskflow.core.navigation.dsl

import androidx.compose.ui.graphics.vector.ImageVector

data class ToolbarAction(
    val icon: ImageVector,
    val contentDescription: String? = null,
    val onClick: () -> Unit,
)