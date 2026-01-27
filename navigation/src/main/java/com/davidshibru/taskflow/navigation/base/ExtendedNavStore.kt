package com.davidshibru.taskflow.navigation.base

import androidx.compose.runtime.Composable
import com.davidshibru.taskflow.core.navigation.dsl.ScreenScope
import com.davidshibru.taskflow.navigation.Routes
import kotlin.reflect.KClass

interface ExtendedNavStore {

    fun <T : Routes> registerConfiguration(
        routeClass: KClass<T>,
        content: ScreenScope.(T) -> Unit
    )

    @Composable
    fun <T : Routes>Content(
        routes: T
    )
}