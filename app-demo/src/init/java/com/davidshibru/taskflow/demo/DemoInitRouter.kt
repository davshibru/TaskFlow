package com.davidshibru.taskflow.demo

import com.davidshibru.taskflow.core.essentials.logger.Logger
import com.davidshibru.taskflow.feature.init.presentation.InitRouter
import javax.inject.Inject

class DemoInitRouter @Inject constructor() : InitRouter {
    override fun launchAuthFlow() {
        Logger.d("Demo init: launch auth flow")
    }

    override fun launchMainFlow() {
        Logger.d("Demo init: launch main flow")
    }
}
