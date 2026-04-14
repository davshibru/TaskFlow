package com.davidshibru.taskflow.demo

import com.davidshibru.taskflow.feature.signup.presentation.SignUpRouter
import javax.inject.Inject

class DemoSignUpRouter @Inject constructor(
    private val demoNavigator: DemoNavigator,
) : SignUpRouter {
    override fun launchCongrats() {
        demoNavigator.launch(CongratsDemoRoute)
    }

    override fun goBackToSignIn() {
        demoNavigator.goBack()
    }
}
