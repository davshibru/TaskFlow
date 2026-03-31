@file:OptIn(ExperimentalCoroutinesApi::class)

package com.davidshibru.taskflow.core.presentation.test

import com.davidshibru.taskflow.core.essentials.exception.ExceptionHandler
import com.davidshibru.taskflow.core.essentials.logger.Logger
import com.davidshibru.taskflow.core.presentation.WithCommonDependencies
import com.davidshibru.taskflow.core.presentation.WithInitCallBack
import com.davidshibru.taskflow.core.presentation.base.AbstractViewModel
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

abstract class AbstractViewModelTest<T : AbstractViewModel> {

    @RelaxedMockK
    protected lateinit var logger: Logger
        private set

    @RelaxedMockK
    protected lateinit var exceptionHandler: ExceptionHandler
        private set

    protected lateinit var scope: TestScope

    protected lateinit var viewModel: T
        private set

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        scope = TestScope()
        Dispatchers.setMain(UnconfinedTestDispatcher(scope.testScheduler))
        viewModel = createViewModel()
        (viewModel as? WithCommonDependencies)
            ?.initDependencies(logger, exceptionHandler)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    protected fun initializeViewModel() {
        (viewModel as? WithInitCallBack)?.initializeViewModel(logger)
    }

    protected fun runTest(block: suspend TestScope.() -> Unit) =
        scope.runTest(testBody = block)

    protected fun runFlowTest(
        timeout: Duration = 1.seconds,
        block: suspend TestScope.() -> Unit
    ) = scope.runTest(timeout = timeout) {
        block()
    }

    abstract fun createViewModel(): T

}