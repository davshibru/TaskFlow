package com.davidshibru.taskflow.core.navigation.routers

import android.content.Context
import android.widget.Toast
import com.davidshibru.taskflow.core.navigation.ChatsRoute
import com.davidshibru.taskflow.core.navigation.SignUpRoute
import com.davidshibru.taskflow.core.navigation.base.AppNavigator
import com.davidshibru.taskflow.core.navigation.base.launchMainFlow
import com.davidshibru.taskflow.feature.chats.presentation.ChatsRouter
import com.davidshibru.taskflow.features.signin.presentation.SignInRouter
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SignInRouterImpl @Inject constructor(
    private val appNavigator: AppNavigator,
    @param:ApplicationContext private val context: Context,
) : SignInRouter{
    override fun launchTermsAndConditions() {
        Toast.makeText(context, "ToDo: Launch Terms and Conditions", Toast.LENGTH_SHORT).show()

    }

    override fun launchPrivacyPolicy() {
        Toast.makeText(context, "ToDo: Launch Privacy Policy", Toast.LENGTH_SHORT).show()

    }

    override fun launchMainFlow() {
        appNavigator.launchMainFlow()
    }

    override fun launchSignUp() {
        appNavigator.launch(SignUpRoute)
    }
}