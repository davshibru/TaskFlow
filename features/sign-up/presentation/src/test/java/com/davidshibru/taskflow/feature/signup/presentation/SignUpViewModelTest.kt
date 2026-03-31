@file:OptIn(ExperimentalCoroutinesApi::class)

package com.davidshibru.taskflow.feature.signup.presentation

import app.cash.turbine.test
import com.davidshibru.taskflow.core.presentation.test.AbstractViewModelTest
import com.davidshibru.taskflow.feature.signup.domain.SignUpUseCase
import com.davidshibru.taskflow.feature.signup.domain.ValidateAccountUseCase
import com.davidshibru.taskflow.feature.signup.domain.entities.InputField
import com.davidshibru.taskflow.feature.signup.domain.entities.NewAccount
import com.davidshibru.taskflow.feature.signup.domain.exceptions.base.AbstractValidationException
import com.davidshibru.taskflow.feature.signup.domain.resources.SignUpStringProvider
import com.davidshibru.taskflow.feature.signup.presentation.SignUpViewModel.Companion.VALIDATION_PERIOD_MILLIS
import io.mockk.awaits
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceTimeBy
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class SignUpViewModelTest : AbstractViewModelTest<SignUpViewModel>() {

    @RelaxedMockK
    private lateinit var signUpUseCase: SignUpUseCase

    @RelaxedMockK
    private lateinit var validateAccountUseCase: ValidateAccountUseCase

    @RelaxedMockK
    private lateinit var router: SignUpRouter

    @RelaxedMockK
    private lateinit var stringProvider: SignUpStringProvider

    override fun createViewModel() = SignUpViewModel(
        signUpUseCase = signUpUseCase,
        validateAccountUseCase = validateAccountUseCase,
        router = router,
        stringProvider = stringProvider
    )

    @Test
    fun `GIVEN view-model is created WHEN instantiate THEN initial state is default`() =
        runFlowTest {
            viewModel.stateFlow.test {
                val expectedIsInProgress = false
                val expectedErrors = emptyMap<InputField<*>, String>()

                val state = awaitItem().unwrap()

                assertEquals(expectedIsInProgress, state.isSignUpInProgress)
                assertEquals(expectedErrors, state.errorMessages)
            }
        }

    @Test
    fun `GIVEN any input account WHEN sign up is executed THEN render progress indicator`() =
        runFlowTest {
            val account = mockk<NewAccount>()
            coEvery { signUpUseCase.invoke(account) } just awaits

            viewModel.executeAction(SignUpAction.SignUp(account))
            viewModel.stateFlow.test {
                val state = awaitItem().unwrap()
                assertTrue(state.isSignUpInProgress)
            }
        }


    @Test
    fun `GIVEN successful account creation WHEN sign up is executed THEN show congrats screen`() {
        val account = mockk<NewAccount>()
        coEvery { signUpUseCase.invoke(account) } just runs

        viewModel.executeAction(SignUpAction.SignUp(account))

        verify(exactly = 1) {
            router.launchCongrats()
        }
    }

    @Test
    fun `GIVEN failed account creation WHEN sign up is executed THEN do not show congrats screen`() {
        val account = mockk<NewAccount>()
        coEvery { signUpUseCase.invoke(account) } throws IllegalStateException()

        viewModel.executeAction(SignUpAction.SignUp(account))

        verify(exactly = 0) {
            router.launchCongrats()
        }
    }

    @Test
    fun `GIVEN failed account creation WHEN sign up is executed THEN handle exception by default`() {
        val account = mockk<NewAccount>()
        val expectedException = IllegalStateException()
        coEvery { signUpUseCase.invoke(account) } throws expectedException

        viewModel.executeAction(SignUpAction.SignUp(account))

        verify(exactly = 1) {
            exceptionHandler.handleException(refEq(expectedException))
        }
    }

    @Test
    fun `GIVEN failed account validation WHEN sign up is executed THEN handle exception by default`() {
        val account = mockk<NewAccount>(relaxed = true)
        val expectedException = mockk<AbstractValidationException>()
        val expectedInputFiled = InputField.RepeatPassword
        val expectedErrorMessage = "error"
        every { expectedException.inputField } returns expectedInputFiled
        every { expectedException.getLocalizedErrorMessage(stringProvider) } returns expectedErrorMessage
        coEvery { signUpUseCase.invoke(account) } throws expectedException

        viewModel.executeAction(SignUpAction.SignUp(account))

        verify(exactly = 1) {
            exceptionHandler.handleException(expectedException)
        }
    }

    @Test
    fun `GIVEN failed account validation WHEN sign up is executed THEN render error`() = runFlowTest {
        val account = mockk<NewAccount>(relaxed = true)
        val expectedException = mockk<AbstractValidationException>()
        val expectedInputFiled = InputField.RepeatPassword
        val expectedErrorMessage = "error"
        every { expectedException.inputField } returns expectedInputFiled
        every { expectedException.getLocalizedErrorMessage(stringProvider) } returns expectedErrorMessage
        coEvery { signUpUseCase.invoke(account) } throws expectedException

        viewModel.executeAction(SignUpAction.SignUp(account))

        viewModel.stateFlow.test {
            val state = awaitItem().unwrap()

            assertEquals(
                mapOf(expectedInputFiled to expectedErrorMessage),
                state.errorMessages
            )
        }

        verify(exactly = 1) {
            exceptionHandler.handleException(expectedException)
        }
    }

    @Test
    fun `GIVEN first validation request WHEN validation executed THEN do not start validation before 1 second`() = runFlowTest {
        val account = mockk<NewAccount>()
        coEvery { validateAccountUseCase.invoke(account) } just awaits
        initializeViewModel()

        viewModel.executeAction(SignUpAction.Validate(account))

        advanceTimeBy(VALIDATION_PERIOD_MILLIS)

        coVerify(exactly = 0) {
            validateAccountUseCase.invoke(any())
        }
    }

    @Test
    fun `GIVEN first validation request WHEN validation executed THEN start validation after 1 second`() = runFlowTest {
        val account = mockk<NewAccount>()
        coEvery { validateAccountUseCase.invoke(account) } just awaits
        initializeViewModel()

        viewModel.executeAction(SignUpAction.Validate(account))

        advanceTimeBy(VALIDATION_PERIOD_MILLIS + 1)

        coVerify(exactly = 1) {
            validateAccountUseCase.invoke(any())
        }
    }

    @Test
    fun `GIVEN fast multiple validation requests WHEN validation executed THEN only last executed`() = runFlowTest {
        val account1 = mockk<NewAccount>()
        val account2 = mockk<NewAccount>()
        val account3 = mockk<NewAccount>()
        coEvery { validateAccountUseCase.invoke(any()) } just awaits
        initializeViewModel()

        viewModel.executeAction(SignUpAction.Validate(account1))
        advanceTimeBy(VALIDATION_PERIOD_MILLIS)
        viewModel.executeAction(SignUpAction.Validate(account2))
        advanceTimeBy(VALIDATION_PERIOD_MILLIS)
        viewModel.executeAction(SignUpAction.Validate(account3))
        advanceTimeBy(VALIDATION_PERIOD_MILLIS)

        coVerify(exactly = 0) {
            validateAccountUseCase.invoke(any())
        }

        advanceTimeBy(1)

        coVerify(exactly = 0) {
            validateAccountUseCase.invoke(refEq(account1))
            validateAccountUseCase.invoke(refEq(account2))
        }
        coVerify(exactly = 1) {
            validateAccountUseCase.invoke(refEq(account3))
        }
    }
}