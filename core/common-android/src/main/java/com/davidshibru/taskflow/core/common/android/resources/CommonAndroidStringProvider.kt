package com.davidshibru.taskflow.core.common.android.resources

import android.content.Context
import com.davidshibru.taskflow.core.essentials.resources.StringProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CommonAndroidStringProvider @Inject constructor(
    @ApplicationContext private val context: Context,
) : StringProvider