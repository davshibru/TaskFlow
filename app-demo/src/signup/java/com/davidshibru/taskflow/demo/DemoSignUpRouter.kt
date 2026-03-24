package com.davidshibru.taskflow.demo

import com.davidshibru.taskflow.core.essentials.logger.Logger
import com.davidshibru.taskflow.feature.signup.presentation.SignUpRouter
import javax.inject.Inject

class DemoSignUpRouter @Inject constructor() : SignUpRouter {
    override fun navigateBack() {
        Logger.d("Успешный вход в SignIn")
    }
}