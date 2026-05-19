package com.davidshibru.taskflow.core.essentials.entities

interface UserId: Id {

    private class Default(value: String): AbstractId(value), UserId

    companion object {
        operator fun invoke(value: String): UserId = Default(value)
    }

}