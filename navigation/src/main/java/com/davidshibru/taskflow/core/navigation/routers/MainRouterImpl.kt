package com.davidshibru.taskflow.core.navigation.routers

import com.davidshibru.taskflow.features.main.presentation.MainRouter
import com.davidshibru.taskflow.core.navigation.base.AppNavigator
import javax.inject.Inject

class MainRouterImpl @Inject constructor(
    private val appNavigator: AppNavigator,
) : MainRouter {

    override fun navigateBack() {
        appNavigator.goBack()
    }
}