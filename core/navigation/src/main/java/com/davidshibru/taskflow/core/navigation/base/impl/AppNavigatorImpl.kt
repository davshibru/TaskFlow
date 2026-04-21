package com.davidshibru.taskflow.core.navigation.base.impl

import com.davidshibru.taskflow.core.essentials.datetime.DateTimeProvider
import com.davidshibru.taskflow.core.navigation.Route
import com.davidshibru.taskflow.core.navigation.base.AppNavigator
import com.davidshibru.taskflow.core.navigation.base.NavigationIntent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppNavigatorImpl @Inject constructor(
    private val dateTimeProvider: DateTimeProvider,
) : AppNavigator {
    private val _navigationEvents = Channel<NavigationIntent>(Channel.Factory.BUFFERED)
    override val navigationEvents: Flow<NavigationIntent> = _navigationEvents.receiveAsFlow()
    private var lastNavigationIntent: NavigationIntent? = null
    private var lastNavigationTimestampMillis = 0L

    override fun launch(route: Route) {
        sendNavigationIntent(NavigationIntent.NavigateTo(route))
    }

    override fun restart(route: Route) {
        sendNavigationIntent(NavigationIntent.Restart(route))
    }

    override fun replace(route: Route) {
        sendNavigationIntent(NavigationIntent.Replace(route))
    }

    override fun goBack() {
        sendNavigationIntent(NavigationIntent.GoBack)
    }

    private fun sendNavigationIntent(intent: NavigationIntent) {
        val currentTimestampMillis = dateTimeProvider.currentTimeMillis()
        val shouldSkipIntent = intent == lastNavigationIntent &&
                currentTimestampMillis - lastNavigationTimestampMillis < NAVIGATION_INTENT_DELAY_MILLIS

        if (shouldSkipIntent) return

        if (_navigationEvents.trySend(intent).isSuccess) {
            lastNavigationIntent = intent
            lastNavigationTimestampMillis = currentTimestampMillis
        }
    }

    private companion object {
        const val NAVIGATION_INTENT_DELAY_MILLIS = 300L
    }
}
