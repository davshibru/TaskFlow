package com.davidshibru.taskflow.demo

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject
import javax.inject.Singleton

data class DemoBackStackEntry(
    val id: String,
    val route: DemoRoute,
)

@Singleton
class DemoNavigator @Inject constructor() {

    private val nextEntryId = AtomicInteger()
    private val _backStack = MutableStateFlow<List<DemoBackStackEntry>>(emptyList())

    val backStack: StateFlow<List<DemoBackStackEntry>> = _backStack

    fun setStartDestination(route: DemoRoute) {
        _backStack.update { backStack ->
            backStack.ifEmpty { listOf(createEntry(route)) }
        }
    }

    fun launch(route: DemoRoute) {
        _backStack.update { backStack ->
            backStack + createEntry(route)
        }
    }

    fun replace(route: DemoRoute) {
        _backStack.update { backStack ->
            if (backStack.isEmpty()) {
                listOf(createEntry(route))
            } else {
                backStack.dropLast(1) + createEntry(route)
            }
        }
    }

    fun restart(route: DemoRoute) {
        _backStack.value = listOf(createEntry(route))
    }

    fun goBack() {
        _backStack.update { backStack ->
            if (backStack.size > 1) {
                backStack.dropLast(1)
            } else {
                backStack
            }
        }
    }

    private fun createEntry(route: DemoRoute): DemoBackStackEntry {
        return DemoBackStackEntry(
            id = nextEntryId.getAndIncrement().toString(),
            route = route,
        )
    }
}
