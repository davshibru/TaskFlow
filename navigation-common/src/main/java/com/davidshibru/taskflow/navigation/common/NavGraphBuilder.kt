package com.davidshibru.taskflow.navigation.common

import androidx.navigation3.runtime.NavKey
import com.davidshibru.taskflow.core.navigation.dsl.BaseRoute
import com.davidshibru.taskflow.core.navigation.dsl.ScreenScope
import kotlin.reflect.KClass

interface NavGraphBuilder<T> where T : NavKey, T : BaseRoute {
    fun <R : T> composable(
        routeClass: KClass<R>,
        content: ScreenScope.(R) -> Unit,
    )
}

inline fun <T, reified R : T> NavGraphBuilder<T>.composable(
    noinline content: ScreenScope.(R) -> Unit,
) where T : NavKey, T : BaseRoute = composable(R::class, content)
