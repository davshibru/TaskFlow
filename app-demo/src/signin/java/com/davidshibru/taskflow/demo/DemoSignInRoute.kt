package com.davidshibru.taskflow.demo

import com.davidshibru.taskflow.core.essentials.logger.Logger
import com.davidshibru.taskflow.features.signin.presentation.SignInRouter
import javax.inject.Inject


class DemoSignInRoute @Inject constructor() : SignInRouter{
    override fun launchTermsAndConditions() {
        Logger.d("Успешный вход в Terms and Conditions")
    }

    override fun launchPrivacyPolicy() {
        Logger.d("Успешный вход в Privacy Policy")
    }

    override fun launchMain() {
        Logger.d("Успешный вход в Main")
    }
}