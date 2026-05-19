package com.davidshibru.taskflow.core.essentials.entities

interface Id {
    val value: String

    fun interface Scope {
        fun generateId(): Id
    }

    private class DefaultId(value: String): AbstractId(value)

    companion object {
        val Empty = Id(0)

        fun <T> idGenerator(block: Scope.() -> T): T {
            var seq = 0L
            val scopeImpl = Scope {
                Id(++seq)
            }

            return block(scopeImpl)
        }

        operator fun invoke(value: String): Id = DefaultId(value)

        operator fun invoke(id: Long): Id = invoke(id.toString())
    }
}

abstract class AbstractId(
    override val value: String,
): Id {

    override fun toString(): String {
        return value
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AbstractId) return false
        if (this::class != other::class) return false

        return value == other.value
    }

    override fun hashCode(): Int {
        return 31 * this::class.hashCode() + value.hashCode()
    }
}
