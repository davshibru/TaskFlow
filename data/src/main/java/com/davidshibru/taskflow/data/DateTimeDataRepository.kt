package com.davidshibru.taskflow.data

import java.time.ZonedDateTime

interface DateTimeDataRepository {
    fun now(): ZonedDateTime
}