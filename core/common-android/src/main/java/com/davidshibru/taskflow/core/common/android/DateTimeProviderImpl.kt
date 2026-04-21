package com.davidshibru.taskflow.core.common.android

import com.davidshibru.taskflow.core.essentials.datetime.DateTimeProvider
import java.time.ZonedDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DateTimeProviderImpl @Inject constructor(): DateTimeProvider {
    override fun now(): ZonedDateTime = ZonedDateTime.now()
    override fun currentTimeMillis() = System.currentTimeMillis()
}