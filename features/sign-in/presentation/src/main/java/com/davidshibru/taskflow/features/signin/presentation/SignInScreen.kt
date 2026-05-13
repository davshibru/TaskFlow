package com.davidshibru.taskflow.features.signin.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import com.davidshibru.taskflow.core.essentials.container.Container
import com.davidshibru.taskflow.core.essentials.logger.Logger
import com.davidshibru.taskflow.core.navigation.dsl.ScreenScope
import com.davidshibru.taskflow.core.navigation.dsl.toolbar
import com.davidshibru.taskflow.core.navigation.dsl.viewModel
import com.davidshibru.taskflow.core.theme.Dimens
import com.davidshibru.taskflow.core.theme.components.ContainerView
import com.davidshibru.taskflow.core.theme.components.ProgressButton
import com.davidshibru.taskflow.core.theme.previews.PreviewScreenContent
import com.davidshibru.taskflow.core.theme.previews.ScreenPreview
import com.davidshibru.taskflow.features.signin.domain.entities.Credentials
import com.davidshibru.taskflow.features.signin.domain.entities.InputField

fun ScreenScope.signInScreen() {
    content {
        val viewModel = viewModel<SignInViewModel>()

        toolbar {
            title = "Sign in"
            action(Icons.Default.Info, "first") {
                Logger.d("ℹ️ Нажата кнопка Info в Тулбаре")
            }
            action(Icons.Default.Info, "second") {
                Logger.d("ℹ️ Нажата кнопка Info в Тулбаре")
            }
            action(Icons.Default.Info, "third") {
                Logger.d("ℹ️ Нажата кнопка Info в Тулбаре")
            }
            contextMenuAction {
                icon = Icons.Default.MoreVert
                item {
                    titleRes = R.string.terms_and_conditions
                    onClick = {
                        Logger.d("ℹ️ Нажата кнопка Terms and conditions в контекстном меню")
                    }
                }
                item {
                    titleRes = R.string.privacy_policy
                    onClick = {
                        Logger.d("ℹ️ Нажата кнопка Privacy policy в контекстном меню")
                    }
                }
            }
        }

        val container: Container<SignInViewModel.State> by viewModel.stateFlow.collectAsState()

        ContainerView(
            container = container,
        ) { state ->
            SignInContent(
                state = state,
                onSignInAction = viewModel::signIn,
                onClearErrorMessage = viewModel::clearErrorMessages,
                onLaunchSignUpAction = viewModel::onLaunchSignUp
            )
        }
    }

}

@Composable
private fun BoxScope.SignInContent(
    state: SignInViewModel.State,
    onSignInAction: (Credentials) -> Unit,
    onLaunchSignUpAction: () -> Unit,
    onClearErrorMessage: () -> Unit,
) {
    var login by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    val loginError =
        if (state.emptyFieldError?.field == InputField.Login) state.emptyFieldError.message else null
    val passwordError =
        if (state.emptyFieldError?.field == InputField.Password) state.emptyFieldError.message else null

    Column(
        verticalArrangement = Arrangement.spacedBy(
            space = Dimens.MediumSpace, alignment = Alignment.CenterVertically
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimens.MediumPadding)
            .align(Alignment.Center)
    ) {
        OutlinedTextField(
            value = login,
            onValueChange = {
                login = it
                if (loginError != null) {
                    onClearErrorMessage()
                }
            },
            label = { Text(stringResource(R.string.login)) },
            isError = loginError != null,
            supportingText = {
                if (loginError != null) {
                    Text(loginError, color = MaterialTheme.colorScheme.error)
                }
            },
            singleLine = true,
        )

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                if (passwordError != null) {
                    onClearErrorMessage()
                }
            },
            label = { Text(stringResource(R.string.password)) },
            isError = passwordError != null,
            supportingText = {
                if (passwordError != null) {
                    Text(passwordError, color = MaterialTheme.colorScheme.error)
                }
            },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
        )

        ProgressButton(
            text = stringResource(R.string.sign_in),
            isInProgress = state.isLoginInProgress,
            onClick = { onSignInAction(Credentials(login, password)) }
        )

        val signUpSuggestion = buildAnnotatedString {
            append(stringResource(R.string.sign_in_don_t_have_an_account))
            appendLine()
            val startPosition = length
            append(stringResource(R.string.sign_in_create_it))
            val endPosition = length
            addLink(
                clickable = LinkAnnotation.Clickable(
                    tag = "sign-up",
                    styles = TextLinkStyles(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                            textDecoration = TextDecoration.Underline,
                        ),
                    ),
                    linkInteractionListener = { onLaunchSignUpAction.invoke() }
                ),
                start = startPosition,
                end = endPosition,
            )
        }
        Text(
            text = signUpSuggestion,
            textAlign = TextAlign.Center,
        )
    }
}

@ScreenPreview
@Composable
private fun SignInContentPreview()  = PreviewScreenContent {
    Box(Modifier.fillMaxSize()) {
        SignInContent(
            state = SignInViewModel.State(),
            onSignInAction = {},
            onClearErrorMessage = {},
            onLaunchSignUpAction = {},
        )
    }
}
