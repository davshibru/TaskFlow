package com.davidshibru.taskflow.core.common.android

import android.content.Context
import com.davidshibru.taskflow.core.essentials.resources.CoreStringProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class CoreStringProviderImpl @Inject constructor(
    @ApplicationContext private val context: Context,
): CoreStringProvider {
    override val unknownErrorMessage: String
        get() = context.getString(R.string.unknown_error_message)

    override val connectionErrorMessage: String
        get() = context.getString(R.string.connection_error_message)

    override fun backendErrorMessage(code: Int, backendMessage: String)
     = context.getString(R.string.backend_error_massage, code, backendMessage)
}