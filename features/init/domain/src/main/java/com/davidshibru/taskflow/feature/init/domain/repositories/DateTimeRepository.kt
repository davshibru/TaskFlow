package com.davidshibru.taskflow.feature.init.domain.repositories

import java.time.ZonedDateTime

interface DateTimeRepository {
    fun now(): ZonedDateTime
}