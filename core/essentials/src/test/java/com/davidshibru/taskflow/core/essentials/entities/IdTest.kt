package com.davidshibru.taskflow.core.essentials.entities

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test

class IdTest {

    @Test
    fun `GIVEN two user ids with same value WHEN compare THEN they are equal`() {
        val first = UserId("@alice:server")
        val second = UserId("@alice:server")

        assertEquals(first, second)
        assertEquals(first.hashCode(), second.hashCode())
    }

    @Test
    fun `GIVEN different id types with same value WHEN compare THEN they are not equal`() {
        val userId = UserId("@alice:server")
        val anotherIdType = AnotherIdType("@alice:server")

        assertFalse(userId == anotherIdType)
    }

    private interface AnotherIdType: Id {
        private class Default(value: String): AbstractId(value), AnotherIdType

        companion object {
            operator fun invoke(value: String): AnotherIdType = Default(value)
        }
    }
}
