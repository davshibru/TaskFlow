package com.davidshibru.taskflow.demo

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject
import javax.inject.Singleton

sealed interface DemoNavigationIntent {
    data class NavigateTo(val route: DemoRoute) : DemoNavigationIntent
    data class Restart(val route: DemoRoute) : DemoNavigationIntent
    data class Replace(val route: DemoRoute) : DemoNavigationIntent
    data object GoBack : DemoNavigationIntent
}

@Singleton
class DemoNavigator @Inject constructor() {

    private val _navigationEvents = Channel<DemoNavigationIntent>(Channel.Factory.BUFFERED)

    val navigationEvents: Flow<DemoNavigationIntent> = _navigationEvents.receiveAsFlow()

    fun launch(route: DemoRoute) {
        _navigationEvents.trySend(DemoNavigationIntent.NavigateTo(route))
    }

    fun replace(route: DemoRoute) {
        _navigationEvents.trySend(DemoNavigationIntent.Replace(route))
    }

    fun restart(route: DemoRoute) {
        _navigationEvents.trySend(DemoNavigationIntent.Restart(route))
    }

    fun goBack() {
        _navigationEvents.trySend(DemoNavigationIntent.GoBack)
    }
}
