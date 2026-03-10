package com.davidshibru.taskflow.feature.init.domain.resources

import com.davidshibru.taskflow.core.essentials.resources.StringProvider

interface InitStringProvider : StringProvider {
    val deviceIsRootedErrorMessage: String
}