package com.davidshibru.taskflow.core.essentials.container

import app.cash.turbine.test
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ContainerFlowExtensionsTest {

    @Test
    fun `GIVEN completed container WHEN retry THEN emit loading before next value`() = runTest {
        val inputFlow = MutableSharedFlow<String>()
        val stateFlow = inputFlow.asContainerStateFlow(
            scope = backgroundScope,
            started = SharingStarted.Eagerly,
        )
        runCurrent()

        stateFlow.test {
            assertEquals(Container.Loading, awaitItem())

            inputFlow.emit("first")
            val first = awaitItem() as Container.Success
            assertEquals("first", first.value)
            assertFalse(first.isLoading)

            first.retry()
            assertEquals(Container.Loading, awaitItem())

            inputFlow.emit("second")
            val second = awaitItem() as Container.Success
            assertEquals("second", second.value)
            assertFalse(second.isLoading)
        }
    }

    @Test
    fun `GIVEN completed container WHEN silent retry THEN keep value and mark loading`() = runTest {
        val inputFlow = MutableSharedFlow<String>()
        val stateFlow = inputFlow.asContainerStateFlow(
            scope = backgroundScope,
            started = SharingStarted.Eagerly,
        )
        runCurrent()

        stateFlow.test {
            assertEquals(Container.Loading, awaitItem())

            inputFlow.emit("first")
            val first = awaitItem() as Container.Success
            assertEquals("first", first.value)
            assertFalse(first.isLoading)

            first.retry(silently = true)
            val silentlyReloading = awaitItem() as Container.Success
            assertEquals("first", silentlyReloading.value)
            assertTrue(silentlyReloading.isLoading)

            inputFlow.emit("second")
            val second = awaitItem() as Container.Success
            assertEquals("second", second.value)
            assertFalse(second.isLoading)
        }
    }
}
