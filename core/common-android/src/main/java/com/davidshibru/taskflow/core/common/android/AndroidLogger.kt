package com.davidshibru.taskflow.core.common.android

import com.davidshibru.taskflow.core.essentials.logger.Logger
import timber.log.Timber

object AndroidLogger : Logger {
    override fun d(message: String) {
        Timber.d(message)
    }

    override fun e(exception: Exception, message: String) {
        Timber.d(exception, message)
    }
}