package com.davidshibru.taskflow.features.profile.presentation.resources

import android.content.Context
import com.davidshibru.taskflow.features.profile.domain.resources.ProfileStringProvider
import com.davidshibru.taskflow.features.profile.presentation.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ProfileStringProviderImpl @Inject constructor(
    @param:ApplicationContext private val context: Context,
) : ProfileStringProvider {

    override val defaultErrorMessage: String
        get() = context.getString(R.string.profile_default_error)
}