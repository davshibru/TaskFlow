package com.davidshibru.taskflow.features.main.presentation.resources

import android.content.Context
import com.davidshibru.taskflow.features.main.domain.resources.MainStringProvider
import com.davidshibru.taskflow.features.main.presentation.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class MainStringProviderImpl @Inject constructor(
    @param:ApplicationContext private val context: Context,
) : MainStringProvider {

    override val defaultErrorMessage: String
        get() = context.getString(R.string.main_default_error)
}