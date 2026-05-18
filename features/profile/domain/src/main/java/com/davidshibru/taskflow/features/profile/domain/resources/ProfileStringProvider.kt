package com.davidshibru.taskflow.features.profile.domain.resources

import com.davidshibru.taskflow.core.essentials.resources.StringProvider

interface ProfileStringProvider : StringProvider {
    val defaultErrorMessage: String
}