package com.davidshibru.taskflow.demo

import com.davidshibru.taskflow.core.navigation.dsl.ScreenScope
import kotlin.reflect.KClass

interface DemoNavGraphBuilder {

    fun <T : DemoRoute> composable(
        routeClass: KClass<T>,
        content: ScreenScope.(T) -> Unit,
    )
}

inline fun <reified T : DemoRoute> DemoNavGraphBuilder.composable(
    noinline content: ScreenScope.(T) -> Unit,
) = composable(T::class, content)
