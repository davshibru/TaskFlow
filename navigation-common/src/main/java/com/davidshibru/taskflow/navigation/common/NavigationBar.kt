package com.davidshibru.taskflow.navigation.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.davidshibru.taskflow.core.navigation.dsl.ScreenNavigationBar

@Composable
fun AppNavigationBar(
    navigationBar: ScreenNavigationBar.Default,
) {
    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.primary,
    ) {
        navigationBar.buttons.forEach { button ->
            NavigationBarItem(
                selected = button.isSelected,
                onClick = button.onClick,
                icon = {
                    Icon(
                        imageVector = button.icon,
                        contentDescription = button.label,
                    )
                },
                label = {
                    Text(button.label)
                },
                colors = NavigationBarItemDefaults.colors(
                    unselectedIconColor = MaterialTheme.colorScheme.onPrimary,
                    unselectedTextColor = MaterialTheme.colorScheme.onPrimary,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                    selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                )
            )
        }
    }
}
