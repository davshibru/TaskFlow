package com.davidshibru.taskflow.core.essentials.exception.mapper

interface ExceptionToMessageMapper {
    fun getLocalizedMessage(exception: Exception) : String

    companion object : ExceptionToMessageMapper {
        private var instance: ExceptionToMessageMapper = EmptyExceptionToMessageMapper()

        override fun getLocalizedMessage(exception: Exception): String {
            return instance.getLocalizedMessage(exception)
        }

        fun set(mapper: ExceptionToMessageMapper) {
            instance = mapper
        }

        fun reset() {
            instance = EmptyExceptionToMessageMapper()
        }
    }
}