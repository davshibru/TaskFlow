package com.davidshibru.taskflow.core.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.davidshibru.taskflow.core.navigation.dsl.ScreenToolbar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppToolBar(
    toolbar: ScreenToolbar.Default,
    showBackButton: Boolean,
    onBackPressed: () -> Unit,
) {
    var isMenuExpanded by remember { mutableStateOf(false) }

    val maxVisibleActions = 2

    val titleText = when {
        toolbar.titleRes != null -> stringResource(id = toolbar.titleRes!!)
        toolbar.title != null -> toolbar.title!!
        else -> ""
    }

    TopAppBar(
        title = {
            Text(text = titleText)
        },
        navigationIcon = {
            if (showBackButton) {
                IconButton(
                    onClick = {
                        onBackPressed()
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "navigate up"
                    )
                }
            }
        },
        actions = {
            val visibleActions = toolbar.actions.take(maxVisibleActions)
            val overflowActions = toolbar.actions.drop(maxVisibleActions)

            val hasOverflow = overflowActions.isNotEmpty()
            val hasContextMenu = toolbar.contextMenuAction != null
            val showThreeDots = hasOverflow || hasContextMenu

            visibleActions.forEach { action ->
                IconButton(onClick = action.onClick) {
                    Icon(
                        imageVector = action.icon,
                        contentDescription = action.contentDescription
                    )
                }
            }

            if (showThreeDots) {
                Box {
                    IconButton(onClick = { isMenuExpanded = true }) {
                        Icon(
                            imageVector = toolbar.contextMenuAction?.icon ?: Icons.Default.MoreVert,
                            contentDescription = "More options"
                        )
                    }

                    DropdownMenu(
                        expanded = isMenuExpanded,
                        onDismissRequest = { isMenuExpanded = false },
                    ) {
                        overflowActions.forEach { action ->
                            DropdownMenuItem(
                                text = { Text(text = action.contentDescription ?: "Action") },
                                leadingIcon = { Icon(action.icon, action.contentDescription) },
                                onClick = {
                                    isMenuExpanded = false
                                    action.onClick()
                                },
                            )
                        }

                        if (hasOverflow && hasContextMenu && toolbar.contextMenuAction!!.items.isNotEmpty()) {
                            HorizontalDivider()
                        }

                        toolbar.contextMenuAction?.items?.forEach { item ->
                            DropdownMenuItem(
                                text = { Text(text = stringResource(id = item.titleRes)) },
                                onClick = {
                                    isMenuExpanded = false
                                    item.onClick
                                },
                            )
                        }
                    }
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
    )
}