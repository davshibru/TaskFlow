package com.davidshibru.taskflow.core.essentials.datetime

import java.time.ZonedDateTime


/**
 * A repository for working with date and time
 */
interface DateTimeProvider {

    /**
     * Get the current system date and time
     */
    fun now(): ZonedDateTime

    fun currentTimeMillis(): Long
}