package com.davidshibru.taskflow.navigation.common

import androidx.navigation3.runtime.NavKey
import com.davidshibru.taskflow.core.navigation.dsl.BaseRoute
import com.davidshibru.taskflow.core.navigation.dsl.ScreenScope
import kotlin.reflect.KClass

class ScreenConfigurationStore {

    private val map = mutableMapOf<KClass<*>, Record<*>>()

    fun <T> registerConfiguration(
        routeClass: KClass<T>,
        configuration: ScreenScope.(T) -> Unit,
    ) where T : NavKey, T : BaseRoute {
        map[routeClass] = Record(configuration)
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : BaseRoute> getConfiguration(route: T): ScreenScope.(T) -> Unit {
        val record = requireNotNull(map[route::class]) {
            "Screen configuration is not registered for ${route::class.qualifiedName}"
        } as Record<T>
        return record.configuration
    }

    private class Record<T : BaseRoute>(
        val configuration: ScreenScope.(T) -> Unit
    )
}
