package com.davidshibru.taskflow.core.essentials.container

import app.cash.turbine.test
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class LazyFlowSubjectTest {

    @Test
    fun `GIVEN custom factory WHEN create subject THEN use custom subject`() = runTest {
        val subjectFactory = object : SubjectFactory {
            @Suppress("UNCHECKED_CAST")
            override fun <T> create(
                loader: suspend LazyFlowSubjectScope<T>.() -> Unit
            ): LazyFlowSubject<T> {
                return TestLazyFlowSubject(flowOf(Container.Success("mocked") as Container<T>))
            }
        }

        val subject = subjectFactory.create<String> {
            emit("real")
        }

        subject.listenReloadable().test {
            assertEquals("mocked", awaitItem().unwrap())
            awaitComplete()
        }
    }

    @Test
    fun `GIVEN default factory WHEN create subject THEN load real value`() = runTest {
        val subject = DefaultSubjectFactory().create<String> {
            emit("real")
        }

        subject.listenReloadable().test {
            assertEquals(Container.Loading, awaitItem())
            assertEquals("real", awaitItem().unwrap())
        }
    }

    @Test
    fun `GIVEN cached value WHEN listen again THEN emit cached value as background load`() = runTest {
        var loadCount = 0
        val subject = DefaultSubjectFactory(cacheTimeoutMillis = 5000L).create<Int> {
            emit(++loadCount)
        }

        subject.listenReloadable().test {
            assertEquals(Container.Loading, awaitItem())
            val first = awaitItem() as Container.Success
            assertEquals(1, first.value)
            assertFalse(first.isLoading)
            cancelAndIgnoreRemainingEvents()
        }

        subject.listenReloadable().test {
            val cached = awaitItem() as Container.Success
            assertEquals(1, cached.value)
            assertTrue(cached.isLoading)

            val reloaded = awaitItem() as Container.Success
            assertEquals(2, reloaded.value)
            assertFalse(reloaded.isLoading)
        }
    }
}

private class TestLazyFlowSubject<T>(
    private val flow: Flow<Container<T>>,
) : LazyFlowSubject<T> {
    override fun listenReloadable(
        emitReloadFunction: Boolean,
        emitBackgroundLoads: Boolean,
    ): Flow<Container<T>> = flow
}
