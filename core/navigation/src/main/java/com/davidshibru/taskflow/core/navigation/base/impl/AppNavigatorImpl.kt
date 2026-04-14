package com.davidshibru.taskflow.core.navigation.base.impl

import com.davidshibru.taskflow.core.navigation.Route
import com.davidshibru.taskflow.core.navigation.base.AppNavigator
import com.davidshibru.taskflow.core.navigation.base.NavigationIntent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppNavigatorImpl @Inject constructor() : AppNavigator {
    private val _navigationEvents = Channel<NavigationIntent>(Channel.Factory.BUFFERED)
    override val navigationEvents: Flow<NavigationIntent> = _navigationEvents.receiveAsFlow()

    override fun launch(route: Route) {
        _navigationEvents.trySend(NavigationIntent.NavigateTo(route))
    }

    override fun restart(route: Route) {
        _navigationEvents.trySend(NavigationIntent.Restart(route))
    }

    override fun replace(route: Route) {
        _navigationEvents.trySend(NavigationIntent.Replace(route))
    }

    override fun goBack() {
        _navigationEvents.trySend(NavigationIntent.GoBack)

    }

}