package com.davidshibru.taskflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.davidshibru.taskflow.core.essentials.exception.ConnectionException
import com.davidshibru.taskflow.core.essentials.exception.mapper.ExceptionToMessageMapper
import com.davidshibru.taskflow.core.essentials.logger.Logger
import com.davidshibru.taskflow.ui.theme.TaskFlowTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var exceptionToMessageMapper: ExceptionToMessageMapper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val message = exceptionToMessageMapper.getLocalizedMessage(ConnectionException())
        val message2 = ExceptionToMessageMapper.getLocalizedMessage(ConnectionException())

        Logger.d(message)
        Logger.d(message2)

        enableEdgeToEdge()
        setContent {
            TaskFlowTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TaskFlowTheme {
        Greeting("Android")
    }
}