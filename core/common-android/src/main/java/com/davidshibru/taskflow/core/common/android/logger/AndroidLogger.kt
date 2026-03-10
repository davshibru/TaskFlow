package com.davidshibru.taskflow.core.common.android.logger

import com.davidshibru.taskflow.core.essentials.logger.Logger
import timber.log.Timber
import javax.inject.Inject

class AndroidLogger @Inject constructor() : Logger {
    override fun d(message: String) {
        Timber.d(message)
    }

    override fun e(exception: Exception, message: String) {
        Timber.e(exception, message)
    }
}