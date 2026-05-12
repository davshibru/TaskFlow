package com.davidshibru.taskflow.demo

import com.davidshibru.taskflow.features.main.presentation.MainRouter
import javax.inject.Inject

class DemoMainRouter @Inject constructor(
    private val demoNavigator: DemoNavigator,
) : MainRouter {

    override fun navigateBack() {
        demoNavigator.goBack()
    }
}