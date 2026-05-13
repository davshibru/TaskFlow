package com.davidshibru.taskflow.demo

import com.davidshibru.taskflow.core.essentials.logger.Logger
import com.davidshibru.taskflow.features.signin.presentation.SignInRouter
import javax.inject.Inject

class DemoMainFeatureSignInRouter @Inject constructor() : SignInRouter {
    override fun launchTermsAndConditions() {
        Logger.d("Demo main feature: launch terms and conditions")
    }

    override fun launchPrivacyPolicy() {
        Logger.d("Demo main feature: launch privacy policy")
    }

    override fun launchMainFlow() {
        Logger.d("Demo main feature: launch main flow")
    }

    override fun launchSignUp() {
        Logger.d("Demo main feature: launch sign up")
    }
}
