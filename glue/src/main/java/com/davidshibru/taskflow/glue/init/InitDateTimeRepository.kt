package com.davidshibru.taskflow.glue.init

import com.davidshibru.taskflow.data.DateTimeDataRepository
import com.davidshibru.taskflow.feature.init.domain.repositories.DateTimeRepository
import javax.inject.Inject

class InitDateTimeRepository @Inject constructor(
    private val dateTimeDataRepository: DateTimeDataRepository,
) : DateTimeRepository, DateTimeDataRepository by dateTimeDataRepository