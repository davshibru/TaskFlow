package com.davidshibru.taskflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.davidshibru.taskflow.core.common.android.AndroidExceptionHandler
import com.davidshibru.taskflow.core.navigation.AppNavHost
import com.davidshibru.taskflow.core.navigation.base.AppNavigator
import com.davidshibru.taskflow.core.navigation.base.impl.ComposeDialogs
import com.davidshibru.taskflow.core.theme.material.TaskFlowTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var appNavigator: AppNavigator

    @Inject
    lateinit var exceptionHandler: AndroidExceptionHandler

    @Inject
    lateinit var composeDialogs: ComposeDialogs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            TaskFlowTheme {
                AppNavHost(modifier = Modifier.fillMaxSize(), appNavigator = appNavigator)
                exceptionHandler.ErrorDialog()
                composeDialogs.Renderer()
            }
        }
    }
}
