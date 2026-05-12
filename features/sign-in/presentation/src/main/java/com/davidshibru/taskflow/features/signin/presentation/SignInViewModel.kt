package com.davidshibru.taskflow.features.signin.presentation

import androidx.lifecycle.viewModelScope
import com.davidshibru.taskflow.core.essentials.container.asContainerStateFlow
import com.davidshibru.taskflow.core.presentation.WithMviState
import com.davidshibru.taskflow.core.presentation.WithMviState.HideProgressPolicy.*
import com.davidshibru.taskflow.core.presentation.base.AbstractViewModel
import com.davidshibru.taskflow.features.signin.domain.SignInUseCase
import com.davidshibru.taskflow.features.signin.domain.entities.Credentials
import com.davidshibru.taskflow.features.signin.domain.entities.InputField
import com.davidshibru.taskflow.features.signin.domain.exceptions.EmptyFieldException
import com.davidshibru.taskflow.features.signin.domain.resources.SignInStringProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val router: SignInRouter,
    private val signInUseCase: SignInUseCase,
    private val signInStringProvider: SignInStringProvider,
) : AbstractViewModel(), WithMviState<SignInViewModel.State> {

    private val _stateFlow = MutableStateFlow(State())
    val stateFlow = combine(_stateFlow, progressStateFlow) { state, inProgress ->
        state.copy(isLoginInProgress = inProgress)
    }.asContainerStateFlow(viewModelScope)

    fun onLaunchSignUp() = router.launchSignUp()


    fun signIn(credentials: Credentials) = launch(onError) {
        try {
            signInUseCase.invoke(credentials)
            router.launchMainFlow()
        } catch (e: EmptyFieldException) {
            updateProgress(false)
            showEmptyFieldErrorMessage(e.inputField)
        }
    }

    fun clearErrorMessages() {
        _stateFlow.update { it.copy(emptyFieldError = null) }
    }

    private fun showEmptyFieldErrorMessage(field: InputField) {
        val emptyErrorMessage = signInStringProvider.emptyFieldError(field)
        val emptyFieldError = EmptyFieldError(field, emptyErrorMessage)
        _stateFlow.update { it.copy(emptyFieldError = emptyFieldError) }
    }

    data class State(
        val isLoginInProgress: Boolean = false,
        val emptyFieldError: EmptyFieldError? = null,
    )

    data class EmptyFieldError(
        val field: InputField,
        val message: String
    )
}