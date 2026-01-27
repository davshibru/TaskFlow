package com.davidshibru.taskflow.navigation.base

import com.davidshibru.taskflow.core.navigation.dsl.ScreenScope
import com.davidshibru.taskflow.navigation.Routes
import kotlin.reflect.KClass

interface ExtendedNavGraphBuilder {

    fun <T: Routes> composable(
        routeClass: KClass<T>,
        content: ScreenScope.(T) -> Unit
    )
}


inline fun <reified T: Routes> ExtendedNavGraphBuilder.composable(
    noinline content: ScreenScope.(T) -> Unit
) = composable(T::class, content)