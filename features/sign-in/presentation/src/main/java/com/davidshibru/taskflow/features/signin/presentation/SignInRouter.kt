package com.davidshibru.taskflow.features.signin.presentation

interface SignInRouter {
    fun launchTermsAndConditions()
    fun launchPrivacyPolicy()
    fun launchMainFlow()
    fun launchSignUp()
}