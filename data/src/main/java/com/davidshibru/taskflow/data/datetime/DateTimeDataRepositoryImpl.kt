package com.davidshibru.taskflow.data.datetime

import com.davidshibru.taskflow.data.DateTimeDataRepository
import java.time.ZonedDateTime
import javax.inject.Inject

class DateTimeDataRepositoryImpl @Inject constructor() : DateTimeDataRepository{
    override fun now(): ZonedDateTime {
        return ZonedDateTime.now()
    }
}