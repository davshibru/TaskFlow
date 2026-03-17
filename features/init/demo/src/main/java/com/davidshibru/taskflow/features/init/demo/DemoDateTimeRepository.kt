package com.davidshibru.taskflow.features.init.demo

import com.davidshibru.taskflow.feature.init.domain.repositories.DateTimeRepository
import java.time.ZoneId
import java.time.ZonedDateTime
import javax.inject.Inject


class DemoDateTimeRepository @Inject constructor() : DateTimeRepository{

    override fun now(): ZonedDateTime = ZonedDateTime.now(ZoneId.of("Asia/Bishkek"))
}