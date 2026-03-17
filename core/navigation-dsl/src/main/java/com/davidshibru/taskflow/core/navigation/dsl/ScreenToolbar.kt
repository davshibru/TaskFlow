package com.davidshibru.taskflow.core.navigation.dsl

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector

data class ContextMenuItem(
    @StringRes val titleRes: Int,
    val onClick: () -> Unit
)

data class ContextMenuAction(
    val icon: ImageVector,
    val items: List<ContextMenuItem>
)

sealed class ScreenToolbar {

    data object Hidden : ScreenToolbar()

    data class Default(
        @StringRes val titleRes: Int? = null,
        val title: String? = null,
        val actions: List<ToolbarAction> = emptyList(),
        val contextMenuAction: ContextMenuAction? = null
    ) : ScreenToolbar()
}