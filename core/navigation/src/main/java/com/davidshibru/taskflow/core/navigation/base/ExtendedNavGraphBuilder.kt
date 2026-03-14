package com.davidshibru.taskflow.core.navigation.base

import com.davidshibru.taskflow.core.navigation.Route
import com.davidshibru.taskflow.core.navigation.dsl.ScreenScope
import kotlin.reflect.KClass

interface ExtendedNavGraphBuilder {

    fun <T : Route> composable(
        routeCLass: KClass<T>,
        content: ScreenScope.(T) -> Unit,
    )

}

inline fun <reified T : Route> ExtendedNavGraphBuilder.composable(
    noinline content: ScreenScope.(T) -> Unit,
) = composable(T::class, content)