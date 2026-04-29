package com.davidshibru.taskflow.core.essentials.flows

import app.cash.turbine.test
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class ThrottleFlowExtensionTest {

    @Test
    fun `GIVEN first item WHEN throttle THEN emit immediately`() = runTest {
        // given
        val period = 1000L
        val inputFlow = MutableSharedFlow<String>()
        val item = "test item"

        // when & then
        inputFlow.throttle(period).test {
            inputFlow.emit(item)

            assertEquals(item, awaitItem())
            
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `GIVEN second item WHEN throttle THEN emit after delay`() = runTest {
        // given
        val period = 1000L
        val inputFlow = MutableSharedFlow<String>()

        val item1 = "item 1"
        val item2 = "item 2"

        // when & then
        inputFlow.throttle(period).test {
            inputFlow.emit(item1)
            assertEquals(item1, awaitItem())

            inputFlow.emit(item2)

            advanceTimeBy(999)
            expectNoEvents()

            advanceTimeBy(1)

            assertEquals(item2, awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `GIVEN second item after large delay WHEN throttle THEN emit immediately`() = runTest {
        // given
        val period = 1000L
        val inputFlow = MutableSharedFlow<String>()

        val item1 = "item 1"
        val item2 = "item 2"

        // when & then
        inputFlow.throttle(period).test {
            inputFlow.emit(item1)
            assertEquals(item1, awaitItem())

            advanceTimeBy(period + 500)

            inputFlow.emit(item2)

            assertEquals(item2, awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }
}
