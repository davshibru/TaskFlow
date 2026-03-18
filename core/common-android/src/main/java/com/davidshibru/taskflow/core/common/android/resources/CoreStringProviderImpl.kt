package com.davidshibru.taskflow.core.common.android.resources

import android.content.Context
import com.davidshibru.taskflow.core.common.android.R
import com.davidshibru.taskflow.core.essentials.resources.CoreStringProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CoreStringProviderImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : CoreStringProvider {
    override val connectionErrorMessage =
        context.getString(R.string.connection_error_message)

    override val unknownErrorMessage = context.getString(R.string.connection_error_message)

    override val invalidBackendResponseMessage =
        context.getString(R.string.invalid_response_from_the_remote_server)

    override fun backendErrorMessage(code: Int, backendMessage: String) =
        context.getString(R.string.backend_error_message, code, backendMessage)

}