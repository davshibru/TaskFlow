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
        screenFunctionName: String,
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
        import $basePackage.core.navigation.dsl.ScreenScope
        import $basePackage.core.navigation.dsl.ScreenToolbar
        import $basePackage.core.theme.components.ContainerView
        
        fun ScreenScope.$screenFunctionName() {
            toolbar = ScreenToolbar.Hidden
            
            content {
                val viewModel: ${featureName}ViewModel = hiltViewModel()
                val container: Container<${featureName}ViewModel.State> by viewModel.stateFlow.collectAsState()
            
                ContainerView(
                    container = container,
                ) { state ->
                    ${featureName}Content(state)
                }
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

    fun entityClass(packageName: String, featureName: String) = """
        package $packageName
        
        data class ${featureName}Entity(
            val id: String = ""
        )
    """.trimIndent()

    fun exceptionClass(packageName: String, featureName: String) = """
        package $packageName
        
        sealed class ${featureName}Exception : Exception() {
            class Default(override val message: String? = null) : ${featureName}Exception()
        }
    """.trimIndent()

    fun repositoryInterface(packageName: String, featureName: String) = """
        package $packageName
        
        interface ${featureName}Repository {
            // TODO: Add repository methods
        }
    """.trimIndent()

    // --- ШАБЛОНЫ ДЛЯ DEMO СЛОЯ ---

    fun demoRepositoryClass(
        packageName: String,
        domainPackageName: String,
        featureName: String,
        basePackage: String = "com.davidshibru.taskflow"
    ) = """
        package $packageName
        
        import $domainPackageName.repositories.${featureName}Repository
        import kotlinx.coroutines.delay
        import javax.inject.Inject
        import javax.inject.Singleton
        
        @Singleton
        class Demo${featureName}Repository @Inject constructor() : ${featureName}Repository {
            
            // TODO: Implement fake methods from ${featureName}Repository
            /* Example:
            override suspend fun execute() {
                delay(2000L) // Fake network delay
            }
            */
        }
    """.trimIndent()

    fun demoHiltModule(
        packageName: String,
        domainPackageName: String,
        featureName: String
    ) = """
        package $packageName
        
        import $domainPackageName.repositories.${featureName}Repository
        import dagger.Binds
        import dagger.Module
        import dagger.hilt.InstallIn
        import dagger.hilt.components.SingletonComponent
        
        @Module
        @InstallIn(SingletonComponent::class)
        interface ${featureName}DemoModule {
        
            @Binds
            fun bind${featureName}Repository(
                impl: Demo${featureName}Repository
            ): ${featureName}Repository
        }
    """.trimIndent()

    fun appRouterClass(
        packageName: String,
        presentationPackageName: String,
        featureName: String
    ) = """
        package $packageName
        
        import $presentationPackageName.${featureName}Router
        import com.davidshibru.taskflow.core.navigation.base.AppNavigator
        import javax.inject.Inject
        
        class ${featureName}RouterImpl @Inject constructor(
            private val appNavigator: AppNavigator,
        ) : ${featureName}Router {
        
            override fun navigateBack() {
                appNavigator.goBack()
            }
        }
    """.trimIndent()

    fun demoRouterClass(
        presentationPackageName: String,
        featureName: String
    ) = """
        package com.davidshibru.taskflow.demo
        
        import $presentationPackageName.${featureName}Router
        import javax.inject.Inject
        
        class Demo${featureName}Router @Inject constructor(
            private val demoNavigator: DemoNavigator,
        ) : ${featureName}Router {
        
            override fun navigateBack() {
                demoNavigator.goBack()
            }
        }
    """.trimIndent()

    fun demoNavigationModule(
        presentationPackageName: String,
        featureName: String
    ) = """
        package com.davidshibru.taskflow.demo
        
        import $presentationPackageName.${featureName}Router
        import dagger.Binds
        import dagger.Module
        import dagger.hilt.InstallIn
        import dagger.hilt.components.SingletonComponent
        
        @Module
        @InstallIn(SingletonComponent::class)
        interface DemoNavigationModule {
        
            @Binds
            fun bind${featureName}Router(impl: Demo${featureName}Router): ${featureName}Router
        }
    """.trimIndent()

    fun demoScreenConfig(
        presentationPackageName: String,
        featureName: String,
        screenFunctionName: String
    ) = """
        package com.davidshibru.taskflow.demo
        
        import androidx.compose.runtime.Composable
        import $presentationPackageName.$screenFunctionName
        import kotlinx.serialization.Serializable
        
        @Serializable
        private data object ${featureName}DemoRoute : DemoRoute
        
        @Composable
        fun DemoScreen(demoNavigator: DemoNavigator) {
            ProvideDemoNavigation(
                navigator = demoNavigator,
                startDestination = ${featureName}DemoRoute,
            ) {
                composable<${featureName}DemoRoute> { $screenFunctionName() }
            }
        }
    """.trimIndent()
}
