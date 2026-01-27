package com.davidshibru.taskflow.feature.signin.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidshibru.taskflow.core.essentials.Container
import com.davidshibru.taskflow.core.essentials.exceptions.handler.ExceptionHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val exceptionHandler: ExceptionHandler,
) : ViewModel() {

    private val _stateFlow = MutableStateFlow<Container<State>>(Container.Success(State()))
    val stateFlow: StateFlow<Container<State>> = _stateFlow.asStateFlow()

    fun signIn() {
        viewModelScope.launch {
            // TODO: Implement sign in logic
        }
    }

    data class State(
        val title: String = "Sign In",
    )
}