package com.davidshibru.taskflow.core.essentials.entities

@JvmInline
value class Id(
    val value: String
) {

    constructor(id: Long) : this(id.toString())

    override fun toString(): String {
        return value
    }

    fun interface Scope {
        fun generateId(): Id
    }

    companion object {
        val Empty = Id(0)

        fun <T> idGenerator(block: Scope.() -> T) : T {
            var seq = 0L
            val scopeImpl = Scope {
                Id(++seq)
            }

            return block(scopeImpl)
        }
    }
}