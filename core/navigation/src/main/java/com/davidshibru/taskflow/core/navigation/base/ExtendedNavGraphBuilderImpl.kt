package com.davidshibru.taskflow.core.navigation.base

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.davidshibru.taskflow.core.navigation.Route
import com.davidshibru.taskflow.core.navigation.dsl.ScreenScope
import kotlin.reflect.KClass

class ExtendedNavGraphBuilderImpl(
    private val navGraphBuilder: NavGraphBuilder,
    private val navStore: ExtendedNavStore,
) : ExtendedNavGraphBuilder {

    override fun <T : Route> composable(
        routeCLass: KClass<T>,
        content: ScreenScope.(T) -> Unit
    ) {
        navStore.registerConfiguration(routeCLass, content)
        navGraphBuilder.composable(routeCLass) { navEntry ->
            val route = navEntry.toRoute<T>(routeCLass)
            navStore.Content(route, navEntry.id)
        }
    }
}