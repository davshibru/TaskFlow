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

    @Test
    fun `GIVEN active listener WHEN reload THEN reload subject`() = runTest {
        var loadCount = 0
        val subject = DefaultSubjectFactory().create<Int> {
            emit(++loadCount)
        }

        subject.listenReloadable().test {
            assertEquals(Container.Loading, awaitItem())
            assertEquals(1, awaitItem().unwrap())

            assertTrue(subject.reload(silently = true))

            val backgroundLoad = awaitItem() as Container.Success
            assertEquals(1, backgroundLoad.value)
            assertTrue(backgroundLoad.isLoading)

            val reloaded = awaitItem() as Container.Success
            assertEquals(2, reloaded.value)
            assertFalse(reloaded.isLoading)
        }
    }

    @Test
    fun `GIVEN active listener WHEN reload async THEN reload subject`() = runTest {
        var loadCount = 0
        val subject = DefaultSubjectFactory().create<Int> {
            emit(++loadCount)
        }

        subject.listenReloadable().test {
            assertEquals(Container.Loading, awaitItem())
            assertEquals(1, awaitItem().unwrap())

            subject.reloadAsync(silently = true)

            val backgroundLoad = awaitItem() as Container.Success
            assertEquals(1, backgroundLoad.value)
            assertTrue(backgroundLoad.isLoading)

            val reloaded = awaitItem() as Container.Success
            assertEquals(2, reloaded.value)
            assertFalse(reloaded.isLoading)
        }
    }

    @Test
    fun `GIVEN active listener WHEN update if success THEN emit updated value`() = runTest {
        val subject = DefaultSubjectFactory().create<List<Int>> {
            emit(listOf(1, 2, 3))
        }

        subject.listenReloadable().test {
            assertEquals(Container.Loading, awaitItem())
            assertEquals(listOf(1, 2, 3), awaitItem().unwrap())

            assertTrue(subject.updateIfSuccess { values ->
                values.filter { it != 2 }
            })

            val updated = awaitItem() as Container.Success
            assertEquals(listOf(1, 3), updated.value)
            assertFalse(updated.isLoading)
        }
    }

    @Test
    fun `GIVEN no success value WHEN update if success THEN skip update`() = runTest {
        val subject = DefaultSubjectFactory().create<Int> {
            emit(1)
        }

        assertFalse(subject.updateIfSuccess { it + 1 })
    }
}

private class TestLazyFlowSubject<T>(
    private val flow: Flow<Container<T>>,
) : LazyFlowSubject<T> {
    override fun listenReloadable(
        emitReloadFunction: Boolean,
        emitBackgroundLoads: Boolean,
    ): Flow<Container<T>> = flow

    override fun reload(silently: Boolean): Boolean = true

    override suspend fun reloadAsync(silently: Boolean) = Unit

    override fun updateIfSuccess(mapper: (T) -> T): Boolean = true
}
