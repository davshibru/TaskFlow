package com.davidshibru.taskflow.core.common.android

import android.app.Application
import com.davidshibru.taskflow.core.essentials.logger.Logger
import timber.log.Timber

abstract class AbstractApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Logger.set(AndroidLogger())
    }
}