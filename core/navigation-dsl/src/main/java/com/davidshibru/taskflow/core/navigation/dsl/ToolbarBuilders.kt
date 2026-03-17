package com.davidshibru.taskflow.core.navigation.dsl

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.ui.graphics.vector.ImageVector

@ToolbarDsl
class ContextMenuItemScope {
    var titleRes: Int = 0
    var onClick: () -> Unit = {}

    fun build() = ContextMenuItem(titleRes, onClick)
}

@ToolbarDsl
class ContextMenuActionScope {
    var icon: ImageVector = Icons.Default.MoreVert
    private val items = mutableListOf<ContextMenuItem>()

    fun item(block: ContextMenuItemScope.() -> Unit) {
        val scope = ContextMenuItemScope().apply(block)
        items.add(scope.build())
    }

    fun build() = ContextMenuAction(icon, items)
}

@ToolbarDsl
class ToolbarScope {
    var titleRes: Int? = null
    var title: String? = null

    private val actions = mutableListOf<ToolbarAction>()
    private var contextMenuAction: ContextMenuAction? = null

    fun contextMenuAction(block: ContextMenuActionScope.() -> Unit) {
        val scope = ContextMenuActionScope().apply(block)
        contextMenuAction = scope.build()
    }

    fun action(icon: ImageVector, contentDescription: String? = null, onClick: () -> Unit) {
        actions.add(ToolbarAction(icon, contentDescription, onClick))
    }

    fun build(): ScreenToolbar.Default {
        return ScreenToolbar.Default(
            titleRes = titleRes,
            title = title,
            actions = actions,
            contextMenuAction = contextMenuAction,
        )
    }
}

fun ScreenScope.toolbar(block: ToolbarScope.() -> Unit) {
    val scope = ToolbarScope().apply(block)
    this.toolbar = scope.build()
}