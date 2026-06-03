@file:OptIn(ExperimentalCoroutinesApi::class)

package com.davidshibru.taskflow.core.essentials.collections

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class CollectionsMapExtensionsTest {

    @Test
    fun `GIVIN any collection WHEN mapNotNullAsync THEN transform each item async`() = runTest {
        val inputList = listOf(1, 2, 3, 4, 5, 6)

        val deferred: Deferred<List<String>> = backgroundScope.async(UnconfinedTestDispatcher(testScheduler)) {
            inputList.mapNotNullAsync { number ->
                delay(1000)
                number.takeIf { it % 2 == 0 }?.toString()
            }
        }

        advanceTimeBy(999)
        assertFalse(deferred.isCompleted)
        advanceTimeBy(2)
        assertTrue(deferred.isCompleted)

        assertEquals(
            listOf("2", "4", "6"),
            deferred.getCompleted(),
        )
    }
}