package com.davidshibru.taskflow.feature.signup.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CongratsViewModel @Inject constructor(
    private val router: SignUpRouter,
) : ViewModel() {

    fun goBackToSignIn() {
        router.goBackToSignIn()
    }
}