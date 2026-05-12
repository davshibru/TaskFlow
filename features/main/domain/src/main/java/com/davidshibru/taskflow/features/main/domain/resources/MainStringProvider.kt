package com.davidshibru.taskflow.features.main.domain.resources

import com.davidshibru.taskflow.core.essentials.resources.StringProvider

interface MainStringProvider : StringProvider {
    val defaultErrorMessage: String
}