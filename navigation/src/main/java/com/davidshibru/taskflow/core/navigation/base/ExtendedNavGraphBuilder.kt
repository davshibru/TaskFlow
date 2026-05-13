package com.davidshibru.taskflow.core.navigation.base

import com.davidshibru.taskflow.core.navigation.Route
import com.davidshibru.taskflow.core.navigation.dsl.ScreenScope
import com.davidshibru.taskflow.navigation.common.NavGraphBuilder

typealias ExtendedNavGraphBuilder = NavGraphBuilder<Route>

inline fun <reified T : Route> ExtendedNavGraphBuilder.composable(
    noinline content: ScreenScope.(T) -> Unit,
) = composable(T::class, content)
