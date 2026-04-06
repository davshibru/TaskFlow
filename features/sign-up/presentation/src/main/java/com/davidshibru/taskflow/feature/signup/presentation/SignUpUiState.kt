package com.davidshibru.taskflow.feature.signup.presentation

import androidx.compose.runtime.Stable
import com.davidshibru.taskflow.feature.signup.domain.entities.InputField
import com.davidshibru.taskflow.feature.signup.domain.entities.NewAccount
import androidx.compose.runtime.State
import com.davidshibru.taskflow.feature.signup.domain.resources.SignUpStringProvider
import kotlinx.collections.immutable.ImmutableMap

@Stable
internal class SignUpUiState(
    private val originState: State<SignUpViewModel.State>,
    private val accountProviderState: State<() -> NewAccount>,
    private val onActionState: State<(SignUpAction) -> Unit>,
) : SignUpViewModel.State {

    private val origin get() = originState.value
    private val onAction get() = onActionState.value
    private val accountProvider get() = accountProviderState.value

    override val isSignUpInProgress: Boolean
        get() = origin.isSignUpInProgress
    override val errorMessages: ImmutableMap<InputField<*>, String>
        get() = origin.errorMessages
    override val stringProvider: SignUpStringProvider
        get() = origin.stringProvider

    val onClearError: (InputField<*>) -> Unit = {
        onAction(SignUpAction.ClearError(it))
    }

    val onEnableErrorMessages: (InputField<*>) -> Unit = {
        onAction(SignUpAction.EnableErrorMessages(it))
    }

    val onValidate: () -> Unit = {
        onAction(SignUpAction.Validate(accountProvider()))
    }
}