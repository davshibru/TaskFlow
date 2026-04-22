package com.davidshibru.taskflow.core.navigation.routers

import com.davidshibru.taskflow.core.navigation.base.AppNavigator
import com.davidshibru.taskflow.feature.chats.presentation.ChatsRouter
import javax.inject.Inject

class ChatsRouterImpl @Inject constructor(
    private val appNavigator: AppNavigator,
) : ChatsRouter {

    override fun navigateBack() {
        appNavigator.goBack()
    }
}
