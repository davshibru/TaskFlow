package com.davidshibru.taskflow.demo

import com.davidshibru.taskflow.navigation.common.NavigationIntent
import com.davidshibru.taskflow.navigation.common.Navigator
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DemoNavigator @Inject constructor() : Navigator<DemoRoute> {

    private val _navigationEvents = Channel<NavigationIntent<DemoRoute>>(Channel.Factory.BUFFERED)

    override val navigationEvents: Flow<NavigationIntent<DemoRoute>> = _navigationEvents.receiveAsFlow()

    override fun launch(route: DemoRoute) {
        _navigationEvents.trySend(NavigationIntent.NavigateTo(route))
    }

    override fun replace(route: DemoRoute) {
        _navigationEvents.trySend(NavigationIntent.Replace(route))
    }

    override fun restart(route: DemoRoute) {
        _navigationEvents.trySend(NavigationIntent.Restart(route))
    }

    override fun goBack() {
        _navigationEvents.trySend(NavigationIntent.GoBack)
    }
}
