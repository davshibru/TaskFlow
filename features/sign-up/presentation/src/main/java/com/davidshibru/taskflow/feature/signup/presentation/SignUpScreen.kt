package com.davidshibru.taskflow.feature.signup.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.davidshibru.taskflow.core.essentials.container.Container
import com.davidshibru.taskflow.core.essentials.logger.Logger
import com.davidshibru.taskflow.core.navigation.dsl.ScreenScope
import com.davidshibru.taskflow.core.navigation.dsl.toolbar
import com.davidshibru.taskflow.core.navigation.dsl.viewModel
import com.davidshibru.taskflow.core.theme.Dimens
import com.davidshibru.taskflow.core.theme.components.ContainerView
import com.davidshibru.taskflow.core.theme.components.ProgressButton
import com.davidshibru.taskflow.core.theme.modifiers.onFocusLost
import com.davidshibru.taskflow.core.theme.previews.PreviewScreenContent
import com.davidshibru.taskflow.core.theme.previews.ScreenPreview
import com.davidshibru.taskflow.feature.signup.domain.entities.InputField
import com.davidshibru.taskflow.feature.signup.domain.entities.NewAccount
import com.davidshibru.taskflow.feature.signup.domain.resources.SignUpStringProvider
import com.davidshibru.taskflow.feature.signup.presentation.resources.SignUpStringProviderImpl
import com.davidshibru.taskflow.feature.signup.presentation.SignUpViewModel.State
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentMapOf


fun ScreenScope.signUpScreen() {
    content {
        val viewModel = viewModel<SignUpViewModel>()

        toolbar {
            title = "Sign up"
            action(Icons.Default.Info, "third") {
                Logger.d("ℹ️ Нажата кнопка Info в Тулбаре")
            }
            contextMenuAction {
                icon = Icons.Default.MoreVert
                item {
                    titleRes = R.string.sign_up_terms_and_conditions
                    onClick = {
                        Logger.d("ℹ️ Нажата кнопка Terms and conditions в контекстном меню")
                    }
                }
                item {
                    titleRes = R.string.sign_up_privacy_policy
                    onClick = {
                        Logger.d("ℹ️ Нажата кнопка Privacy policy в контекстном меню")
                    }
                }
            }
        }

        val container: Container<State> by viewModel.stateFlow.collectAsState()

        ContainerView(
            container = container,
        ) { state ->
            SignUpContent(
                signUpState = state, viewModel::executeAction
            )
        }
    }
}

@Composable
private fun SignUpContent(
    signUpState: State,
    onAction: (SignUpAction) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(Dimens.MediumPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Dimens.MediumPadding, Alignment.CenterVertically)
    ) {

        val loginState = rememberSaveable { mutableStateOf("") }
        val passwordState = rememberSaveable { mutableStateOf("") }
        val repeatPasswordState = rememberSaveable { mutableStateOf("") }
        val firstNameState = rememberSaveable { mutableStateOf("") }
        val lastNameState = rememberSaveable { mutableStateOf("") }
        val ageState = rememberSaveable { mutableStateOf("") }

        val accountProvider: () -> NewAccount = {
            NewAccount(
                login = loginState.value,
                password = passwordState.value,
                repeatPassword = repeatPasswordState.value,
                firstName = firstNameState.value,
                lastName = lastNameState.value,
                age = ageState.value.toIntOrNull() ?: NewAccount.EMPTY_AGE,
            )
        }

        val originState = rememberUpdatedState(signUpState)
        val accountProviderState = rememberUpdatedState(accountProvider)
        val onActionState = rememberUpdatedState(onAction)

        val uiState = remember {
            SignUpUiState(originState, accountProviderState, onActionState)
        }

        with(uiState) {
            SignUpTextField(
                valueState = loginState,
                inputField = InputField.Login,
            )

            SignUpTextField(
                valueState = firstNameState,
                inputField = InputField.FirstName,
            )

            SignUpTextField(
                valueState = lastNameState,
                inputField = InputField.LastName,
            )

            SignUpTextField(
                valueState = ageState,
                inputField = InputField.Age,
                keyboardType = KeyboardType.Number,
            )

            SignUpTextField(
                valueState = passwordState,
                inputField = InputField.Password,
                visualTransformation = PasswordVisualTransformation(),
            )

            SignUpTextField(
                valueState = repeatPasswordState,
                inputField = InputField.RepeatPassword,
                visualTransformation = PasswordVisualTransformation(),
                imeAction = ImeAction.Done,
            )

            ProgressButton(
                isInProgress = isSignUpInProgress,
                text = stringResource(R.string.sign_up_create_account),
                onClick = {
                    onAction(SignUpAction.SignUp(accountProvider()))
                }
            )
        }
    }
}

@Composable
private fun SignUpUiState.SignUpTextField(
    valueState: MutableState<String>,
    inputField: InputField<*>,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next
) {
    OutlinedTextField(
        value = valueState.value,
        onValueChange = {
            valueState.value = it
            onClearError(inputField)
        },
        modifier = Modifier.onFocusLost {
            onEnableErrorMessages(inputField)
            onValidate()
        },
        singleLine = true,
        visualTransformation = visualTransformation,
        enabled = !isSignUpInProgress,
        isError = errorMessages.contains(inputField),
        supportingText = errorMessages[inputField]?.let {
            { Text(it) }
        },
        label = { Text(inputField.fieldName(stringProvider)) },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction,
        )
    )
}

@ScreenPreview
@Composable
private fun SignUpContentPreview() = PreviewScreenContent {
    Box(Modifier.fillMaxSize()) {
        SignUpContent(PreviewState(SignUpStringProviderImpl(LocalContext.current)))
    }
}

private data class PreviewState(
    override val stringProvider: SignUpStringProvider,
) : State {
    override val isSignUpInProgress: Boolean = false
    override val errorMessages: ImmutableMap<InputField<*>, String> = persistentMapOf()
}
