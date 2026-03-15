package com.davidshibru.taskflow.feature.init.domain.repositories

import java.time.ZoneId
import java.time.ZonedDateTime
import javax.inject.Inject

// TODO use interface
class DateTimeRepository @Inject constructor() {

    fun now(): ZonedDateTime = ZonedDateTime.now(ZoneId.of("Asia/Bishkek"))
}