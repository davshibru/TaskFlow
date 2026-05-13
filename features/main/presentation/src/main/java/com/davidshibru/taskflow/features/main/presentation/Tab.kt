package com.davidshibru.taskflow.features.main.presentation

import android.content.Context
import androidx.compose.ui.graphics.vector.ImageVector
import com.davidshibru.taskflow.core.navigation.dsl.BaseRoute

interface Tab : BaseRoute {
    val icon: ImageVector
    val label: Context.() -> String
}
