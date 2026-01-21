package com.davidshibru.taskflow.feature.init.domain

import com.davidshibru.taskflow.core.essentials.resources.StringProvider

interface InitStringProvider : StringProvider {
    val deviceIsRootedMessage: String
}