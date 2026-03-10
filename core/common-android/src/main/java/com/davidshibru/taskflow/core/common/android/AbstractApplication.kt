package com.davidshibru.taskflow.core.common.android

import android.app.Application
import com.davidshibru.taskflow.core.essentials.exception.mapper.ExceptionToMessageMapper
import com.davidshibru.taskflow.core.essentials.logger.Logger
import timber.log.Timber
import javax.inject.Inject

abstract class AbstractApplication : Application() {

    @Inject
    lateinit var logger: Logger

    @Inject
    lateinit var exceptionToMessageMapper: ExceptionToMessageMapper

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        Logger.set(logger)
        ExceptionToMessageMapper.set(exceptionToMessageMapper)
    }
}