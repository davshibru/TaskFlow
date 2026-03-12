package com.davidshibru.template.script

object CodeTemplates {

    fun parentBuildGradle() = """
        tasks.register<Delete>("clean") {
            delete(layout.buildDirectory)
        }
    """.trimIndent()

    fun unitTest(packageName: String, className: String) = """
        package $packageName

        import org.junit.Assert.assertEquals
        import org.junit.Test

        class ${className}Test {
            @Test
            fun `initial test`() {
                assertEquals("Test should pass", 4, 2 + 2)
            }
        }
    """.trimIndent()

    fun useCaseInterface(packageName: String, featureName: String) = """
        package $packageName
        
        interface ${featureName}UseCase {
            operator fun invoke()
        }
    """.trimIndent()

    fun routerInterface(packageName: String, featureName: String) = """
        package $packageName
        
        interface ${featureName}Router {
            fun navigateBack()
        }
    """.trimIndent()

    fun androidClass(packageName: String, className: String) = """
        package $packageName
        
        import android.content.Context
        import android.widget.Toast
        
        class $className {
            fun hello(context: Context) {
                Toast.makeText(context, "Hello from ${className}!", Toast.LENGTH_SHORT).show()
            }
        }
    """.trimIndent()

    fun kotlinClass(packageName: String, className: String) = """
        package $packageName
        
        class $className {
            // TODO: Implement logic for $className
        }
    """.trimIndent()

    fun proguardRules() = """
        # Add project specific ProGuard rules here.
        # By default, the flags in this file are appended to flags specified
        # in ${'$'}ANDROID_HOME/tools/proguard/proguard-android.txt
    """.trimIndent()

    fun viewModelClass(
        packageName: String,
        featureName: String,
        basePackage: String = "com.davidshibru.taskflow" // Твой базовый пакет для импортов Core
    ) = """
        package $packageName
        
        import androidx.lifecycle.ViewModel
        import androidx.lifecycle.viewModelScope
        import $basePackage.core.essentials.container.Container
        import $basePackage.core.essentials.container.asContainerStateFlow
        import dagger.hilt.android.lifecycle.HiltViewModel
        import kotlinx.coroutines.flow.MutableStateFlow
        import kotlinx.coroutines.flow.StateFlow
        import kotlinx.coroutines.flow.map
        import javax.inject.Inject
        
        @HiltViewModel
        class ${featureName}ViewModel @Inject constructor(
            private val router: ${featureName}Router
        ) : ViewModel() {
        
            private val vmStateFlow = MutableStateFlow(ViewModelState())
        
            val stateFlow: StateFlow<Container<State>> = vmStateFlow
                .map { vmState ->
                    State(isLoading = vmState.isLoading)
                }
                .asContainerStateFlow(viewModelScope)
        
            fun onBackClicked() {
                router.navigateBack()
            }
        
            data class State(
                val title: String = "${featureName} Feature",
                val isLoading: Boolean
            )
            
            private data class ViewModelState(
                val isLoading: Boolean = false
            )
        }
    """.trimIndent()

    fun screenClass(
        packageName: String,
        featureName: String,
        basePackage: String = "com.davidshibru.taskflow"
    ) = """
        package $packageName
        
        import androidx.compose.foundation.layout.Box
        import androidx.compose.foundation.layout.fillMaxSize
        import androidx.compose.material3.Text
        import androidx.compose.runtime.Composable
        import androidx.compose.runtime.collectAsState
        import androidx.compose.runtime.getValue
        import androidx.compose.ui.Alignment
        import androidx.compose.ui.Modifier
        import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
        import $basePackage.core.essentials.container.Container
        import $basePackage.core.theme.components.ContainerView
        
        @Composable
        fun ${featureName}Screen(
            viewModel: ${featureName}ViewModel = hiltViewModel()
        ) {
            val container: Container<${featureName}ViewModel.State> by viewModel.stateFlow.collectAsState()
        
            ContainerView(
                container = container,
            ) { state ->
                ${featureName}Content(state)
            }
        }
        
        @Composable
        private fun ${featureName}Content(state: ${featureName}ViewModel.State) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = state.title)
            }
        }
    """.trimIndent()
}