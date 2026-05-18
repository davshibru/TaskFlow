package com.davidshibru.taskflow.core.navigation.routers

import com.davidshibru.taskflow.features.profile.presentation.ProfileRouter
import com.davidshibru.taskflow.core.navigation.base.AppNavigator
import javax.inject.Inject

class ProfileRouterImpl @Inject constructor(
    private val appNavigator: AppNavigator,
) : ProfileRouter {

    override fun navigateBack() {
        appNavigator.goBack()
    }
}