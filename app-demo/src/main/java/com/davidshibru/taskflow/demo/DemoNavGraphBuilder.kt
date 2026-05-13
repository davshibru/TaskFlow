package com.davidshibru.taskflow.demo

import com.davidshibru.taskflow.core.navigation.dsl.ScreenScope
import com.davidshibru.taskflow.navigation.common.NavGraphBuilder

typealias DemoNavGraphBuilder = NavGraphBuilder<DemoRoute>

inline fun <reified T : DemoRoute> DemoNavGraphBuilder.composable(
    noinline content: ScreenScope.(T) -> Unit,
) = composable(T::class, content)
