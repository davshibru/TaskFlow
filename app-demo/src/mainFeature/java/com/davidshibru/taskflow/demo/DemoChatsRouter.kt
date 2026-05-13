package com.davidshibru.taskflow.demo

import com.davidshibru.taskflow.feature.chats.presentation.ChatsRouter
import javax.inject.Inject

class DemoChatsRouter @Inject constructor(
    private val demoNavigator: DemoNavigator,
) : ChatsRouter {

    override fun navigateBack() {
        demoNavigator.goBack()
    }
}
