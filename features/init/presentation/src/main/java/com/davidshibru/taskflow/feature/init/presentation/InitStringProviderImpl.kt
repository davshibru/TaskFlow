package com.davidshibru.taskflow.feature.init.presentation

import android.content.Context
import com.davidshibru.taskflow.feature.init.domain.InitStringProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class InitStringProviderImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : InitStringProvider {

    override val deviceIsRootedMessage: String
        get() = context.getString(R.string.rooted_device_error)
}