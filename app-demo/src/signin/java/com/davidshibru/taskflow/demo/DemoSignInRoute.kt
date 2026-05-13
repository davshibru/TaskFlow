package com.davidshibru.taskflow.demo

import com.davidshibru.taskflow.core.essentials.logger.Logger
import com.davidshibru.taskflow.features.signin.presentation.SignInRouter
import javax.inject.Inject

class DemoSignInRoute @Inject constructor() : SignInRouter {
    override fun launchTermsAndConditions() {
        Logger.d("Demo sign in: launch terms and conditions")
    }

    override fun launchPrivacyPolicy() {
        Logger.d("Demo sign in: launch privacy policy")
    }

    override fun launchMainFlow() {
        Logger.d("Demo sign in: launch main flow")
    }

    override fun launchSignUp() {
        Logger.d("Demo sign in: launch sign up")
    }
}
