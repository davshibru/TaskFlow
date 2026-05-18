package com.davidshibru.taskflow.demo

import com.davidshibru.taskflow.features.profile.presentation.ProfileRouter
import javax.inject.Inject

class DemoProfileRouter @Inject constructor(
    private val demoNavigator: DemoNavigator,
) : ProfileRouter {

    override fun navigateBack() {
        demoNavigator.goBack()
    }
}
