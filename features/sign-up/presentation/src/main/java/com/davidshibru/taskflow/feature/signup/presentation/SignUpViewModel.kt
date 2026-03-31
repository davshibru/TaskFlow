package com.davidshibru.taskflow.feature.signup.presentation

import androidx.lifecycle.viewModelScope
import com.davidshibru.taskflow.core.essentials.container.Container
import com.davidshibru.taskflow.core.essentials.container.asContainerStateFlow
import com.davidshibru.taskflow.core.presentation.WithInitCallBack
import com.davidshibru.taskflow.core.presentation.WithMviState
import com.davidshibru.taskflow.core.presentation.base.AbstractViewModel
import com.davidshibru.taskflow.feature.signup.domain.SignUpUseCase
import com.davidshibru.taskflow.feature.signup.domain.ValidateAccountUseCase
import com.davidshibru.taskflow.feature.signup.domain.entities.InputField
import com.davidshibru.taskflow.feature.signup.domain.entities.InputFieldValue
import com.davidshibru.taskflow.feature.signup.domain.entities.NewAccount
import com.davidshibru.taskflow.feature.signup.domain.entities.ValidationResult
import com.davidshibru.taskflow.feature.signup.domain.entities.toFieldValues
import com.davidshibru.taskflow.feature.signup.domain.exceptions.base.AbstractValidationException
import com.davidshibru.taskflow.feature.signup.domain.resources.SignUpStringProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableMap
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import kotlin.collections.plus

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val validateAccountUseCase: ValidateAccountUseCase,
    private val router: SignUpRouter,
    private val stringProvider: SignUpStringProvider,
) : AbstractViewModel(),
    WithInitCallBack,
    WithMviState<SignUpViewModel.StateImpl> {

    private val _stateFlow = MutableStateFlow(StateImpl())
    val stateFlow: StateFlow<Container<State>> =
        combine(_stateFlow, progressStateFlow) { state, inProgress ->
            state.copy(isSignUpInProgress = inProgress)
        }.asContainerStateFlow(viewModelScope)

    private val validateRequestsFlow = MutableSharedFlow<NewAccount>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )

    override suspend fun onInitialized() {
        validateRequestsFlow
            .debounce(VALIDATION_PERIOD_MILLIS)
            .collect(::validate)

    }

    fun executeAction(action: SignUpAction) {
        when (action) {
            is SignUpAction.SignUp -> signUp(action.account)
            is SignUpAction.Validate -> validateRequestsFlow.tryEmit(action.account)
            is SignUpAction.ClearError -> clearError(action.field)
            is SignUpAction.EnableErrorMessages -> enableErrorMessages(action.field)
        }
    }

    private fun signUp(account: NewAccount) = launch(WithMviState.HideProgressPolicy.onError) {
        try {
            signUpUseCase.invoke(account)
            router.launchCongrats()

        } catch (e: AbstractValidationException) {
            coroutineScope.ensureActive()
            renderValidationException(account, e)
            throw e // show default error dialog
        }
    }

    private suspend fun validate(account: NewAccount) = launchSync {
        val validationResult = validateAccountUseCase.invoke(account)
        _stateFlow.update { currentState ->
            currentState.withNewValidationResult(validationResult)
        }
    }

    private fun clearError(field: InputField<*>) {
        _stateFlow.update { currentState ->
            currentState.clearError(field)
        }
    }

    private fun enableErrorMessages(field: InputField<*>) {
        _stateFlow.update { currentState ->
            currentState.enableErrorMessages(field)
        }
    }

    private fun renderValidationException(account: NewAccount, e: AbstractValidationException) = _stateFlow.update { currentState ->
            currentState.withValidationException(account, e)
    }


    private fun toErrorMessagePair(e: AbstractValidationException) =
        e.inputField to e.getLocalizedErrorMessage(stringProvider)


    private fun StateImpl.withValidationException(account: NewAccount, e: AbstractValidationException) = copy(
        allErrorMessages = (allErrorMessages + toErrorMessagePair(e)),
        fieldsWithEnabledErrors = account.toFieldValues()
            .map(InputFieldValue<*>::inputField)
            .toSet(),
    )

    private fun StateImpl.withNewValidationResult(validationResult: ValidationResult) = copy(
        allErrorMessages = validationResult.toErrorMessagesMap()
    )

    private fun StateImpl.enableErrorMessages(field: InputField<*>) = copy(
        fieldsWithEnabledErrors = fieldsWithEnabledErrors + field
    )

    private fun StateImpl.clearError(field: InputField<*>) = copy(
        allErrorMessages = (allErrorMessages - field)
    )

    private fun ValidationResult.toErrorMessagesMap(): Map<InputField<*>, String> {
        return when (this) {
            is ValidationResult.Failure -> exceptions.associate(::toErrorMessagePair)
            ValidationResult.Success -> emptyMap()
        }
    }

    private suspend fun launchSync(action: suspend () -> Unit) {
        try {
            action()
        } catch (e: Exception) {
            coroutineScope.ensureActive()
            logger.e(e)
        }

    }

    interface State {
        val isSignUpInProgress: Boolean
        val errorMessages: ImmutableMap<InputField<*>, String>
    }

    private data class StateImpl(
        override val isSignUpInProgress: Boolean = false,
        val allErrorMessages: Map<InputField<*>, String> = emptyMap(),
        val fieldsWithEnabledErrors: Set<InputField<*>> = emptySet(),
    ) : State {
        override val errorMessages: ImmutableMap<InputField<*>, String> =
            allErrorMessages.filterKeys(fieldsWithEnabledErrors::contains)
                .toImmutableMap()
    }

    internal companion object {
        const val VALIDATION_PERIOD_MILLIS = 1000L
    }
}