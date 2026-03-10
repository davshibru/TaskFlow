package com.davidshibru.taskflow.feature.init.presentation.resources

import android.content.Context
import com.davidshibru.taskflow.feature.init.domain.resources.InitStringProvider
import com.davidshibru.taskflow.feature.init.presentation.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class InitStringProviderImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : InitStringProvider {

    override val deviceIsRootedErrorMessage = context.getString(R.string.rooted_device_error)

}