package com.davidshibru.taskflow.navigation.base

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.davidshibru.taskflow.core.navigation.dsl.ScreenScope
import com.davidshibru.taskflow.navigation.Routes
import kotlin.reflect.KClass

class ExtendedNavGraphBuilderImpl(
    private val navGraphBuilder: NavGraphBuilder,
    private val navStore: ExtendedNavStore,
) : ExtendedNavGraphBuilder {
    override fun <T : Routes> composable(
        routeClass: KClass<T>,
        content: ScreenScope.(T) -> Unit
    ) {

        navStore.registerConfiguration(routeClass, content)
        navGraphBuilder.composable((routeClass as Routes).feature) { navEntry ->
            val route = navEntry.route(routeClass)
            navStore.Content()
        }
    }
}