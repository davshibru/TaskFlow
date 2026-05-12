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

    fun getUseCaseInterface(packageName: String, featureName: String) = """
        package $packageName
        
        import $packageName.entities.${featureName}Entity
        
        interface Get${featureName}UseCase {
            suspend operator fun invoke(): List<${featureName}Entity>
        }
    """.trimIndent()

    fun saveUseCaseInterface(packageName: String, featureName: String) = """
        package $packageName
        
        import $packageName.entities.${featureName}Entity
        
        interface Save${featureName}UseCase {
            suspend operator fun invoke(entity: ${featureName}Entity)
        }
    """.trimIndent()

    fun getUseCaseImpl(packageName: String, featureName: String) = """
        package $packageName.usecases
        
        import $packageName.Get${featureName}UseCase
        import $packageName.entities.${featureName}Entity
        import $packageName.repositories.${featureName}Repository
        import javax.inject.Inject
        
        internal class Get${featureName}UseCaseImpl @Inject constructor(
            private val repository: ${featureName}Repository,
        ) : Get${featureName}UseCase {
        
            override suspend operator fun invoke(): List<${featureName}Entity> {
                return repository.get${featureName}Items()
            }
        }
    """.trimIndent()

    fun saveUseCaseImpl(packageName: String, featureName: String) = """
        package $packageName.usecases
        
        import $packageName.Save${featureName}UseCase
        import $packageName.entities.${featureName}Entity
        import $packageName.repositories.${featureName}Repository
        import javax.inject.Inject
        
        internal class Save${featureName}UseCaseImpl @Inject constructor(
            private val repository: ${featureName}Repository,
        ) : Save${featureName}UseCase {
        
            override suspend operator fun invoke(entity: ${featureName}Entity) {
                repository.save${featureName}Item(entity)
            }
        }
    """.trimIndent()

    fun useCasesModule(packageName: String, featureName: String) = """
        package $packageName.di
        
        import $packageName.Get${featureName}UseCase
        import $packageName.Save${featureName}UseCase
        import $packageName.usecases.Get${featureName}UseCaseImpl
        import $packageName.usecases.Save${featureName}UseCaseImpl
        import dagger.Binds
        import dagger.Module
        import dagger.hilt.InstallIn
        import dagger.hilt.components.SingletonComponent
        
        @Module
        @InstallIn(SingletonComponent::class)
        internal interface UseCasesModule {
        
            @Binds
            fun bindGet${featureName}UseCase(impl: Get${featureName}UseCaseImpl): Get${featureName}UseCase
        
            @Binds
            fun bindSave${featureName}UseCase(impl: Save${featureName}UseCaseImpl): Save${featureName}UseCase
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
        basePackage: String = "com.davidshibru.taskflow"
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
        import kotlinx.coroutines.flow.update
        import javax.inject.Inject
        
        @HiltViewModel
        class ${featureName}ViewModel @Inject constructor(
            private val router: ${featureName}Router,
        ) : ViewModel() {
        
            private val vmStateFlow = MutableStateFlow(ViewModelState())
        
            val stateFlow: StateFlow<Container<State>> = vmStateFlow
                .map { vmState ->
                    State(
                        title = "$featureName",
                        counter = vmState.counter,
                        isDecrementEnabled = vmState.counter > 0,
                    )
                }
                .asContainerStateFlow(viewModelScope)
        
            fun onIncrementClicked() {
                vmStateFlow.update { state ->
                    state.copy(counter = state.counter + 1)
                }
            }
        
            fun onDecrementClicked() {
                vmStateFlow.update { state ->
                    state.copy(counter = (state.counter - 1).coerceAtLeast(0))
                }
            }
        
            fun onResetClicked() {
                vmStateFlow.update { state ->
                    state.copy(counter = 0)
                }
            }
        
            fun onBackClicked() {
                router.navigateBack()
            }
        
            data class State(
                val title: String,
                val counter: Int,
                val isDecrementEnabled: Boolean,
            )
            
            private data class ViewModelState(
                val counter: Int = 0,
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
        
        import androidx.compose.foundation.layout.Arrangement
        import androidx.compose.foundation.layout.Column
        import androidx.compose.foundation.layout.Row
        import androidx.compose.foundation.layout.fillMaxSize
        import androidx.compose.foundation.layout.padding
        import androidx.compose.material3.Button
        import androidx.compose.material3.MaterialTheme
        import androidx.compose.material3.OutlinedButton
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
        import $basePackage.core.theme.Dimens
        import $basePackage.core.theme.components.ContainerView
        import $basePackage.core.theme.previews.PreviewScreenContent
        import $basePackage.core.theme.previews.ScreenPreview
        
        fun ScreenScope.$screenFunctionName() {
            toolbar = ScreenToolbar.Default(title = "$featureName")
            
            content {
                val viewModel: ${featureName}ViewModel = hiltViewModel()
                val container: Container<${featureName}ViewModel.State> by viewModel.stateFlow.collectAsState()
            
                ContainerView(
                    modifier = Modifier.fillMaxSize(),
                    container = container,
                ) { state ->
                    ${featureName}Content(
                        state = state,
                        onIncrement = viewModel::onIncrementClicked,
                        onDecrement = viewModel::onDecrementClicked,
                        onReset = viewModel::onResetClicked,
                    )
                }
            }
        }
        
        @Composable
        private fun ${featureName}Content(
            state: ${featureName}ViewModel.State,
            modifier: Modifier = Modifier,
            onIncrement: () -> Unit = {},
            onDecrement: () -> Unit = {},
            onReset: () -> Unit = {},
        ) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(Dimens.MediumPadding),
                verticalArrangement = Arrangement.spacedBy(
                    space = Dimens.MediumSpace,
                    alignment = Alignment.CenterVertically,
                ),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = state.title,
                    style = MaterialTheme.typography.headlineSmall,
                )
                Text(
                    text = "Counter: ${'$'}{state.counter}",
                    style = MaterialTheme.typography.displaySmall,
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(Dimens.SmallSpace),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    OutlinedButton(
                        enabled = state.isDecrementEnabled,
                        onClick = onDecrement,
                    ) {
                        Text(text = "-")
                    }
                    Button(onClick = onIncrement) {
                        Text(text = "+")
                    }
                }
                OutlinedButton(onClick = onReset) {
                    Text(text = "Reset")
                }
            }
        }
        
        @ScreenPreview
        @Composable
        private fun ${featureName}ContentPreview() = PreviewScreenContent {
            ${featureName}Content(
                state = ${featureName}ViewModel.State(
                    title = "$featureName",
                    counter = 3,
                    isDecrementEnabled = true,
                )
            )
        }
    """.trimIndent()

    fun entityClass(packageName: String, featureName: String) = """
        package $packageName
        
        import com.davidshibru.taskflow.core.essentials.entities.Id
        
        data class ${featureName}Entity(
            val id: Id = Id.Empty,
            val title: String = "",
        )
    """.trimIndent()

    fun abstractFeatureExceptionClass(packageName: String, domainPackageName: String, featureName: String) = """
        package $packageName
        
        import com.davidshibru.taskflow.core.essentials.exception.base.AbstractAppException
        import com.davidshibru.taskflow.core.essentials.exception.base.WithLocalizedMessage
        import com.davidshibru.taskflow.core.essentials.resources.StringProviderStore
        import $domainPackageName.resources.${featureName}StringProvider
        
        abstract class Abstract${featureName}AppException(
            message: String,
            cause: Throwable? = null,
        ) : AbstractAppException(message, cause), WithLocalizedMessage {
        
            override fun getLocalizedErrorMessage(stringProvider: StringProviderStore): String {
                return getLocalizedErrorMessage(stringProvider<${featureName}StringProvider>())
            }
        
            abstract fun getLocalizedErrorMessage(stringProvider: ${featureName}StringProvider): String
        }
    """.trimIndent()

    fun defaultFeatureExceptionClass(packageName: String, featureName: String) = """
        package $packageName
        
        import $packageName.base.Abstract${featureName}AppException
        import ${packageName.substringBeforeLast(".")}.resources.${featureName}StringProvider
        
        class Default${featureName}Exception(
            message: String = "Default $featureName error",
            cause: Throwable? = null,
        ) : Abstract${featureName}AppException(message, cause) {
        
            override fun getLocalizedErrorMessage(stringProvider: ${featureName}StringProvider): String {
                return stringProvider.defaultErrorMessage
            }
        }
    """.trimIndent()

    fun repositoryInterface(packageName: String, featureName: String) = """
        package $packageName
        
        import ${packageName.substringBeforeLast(".")}.entities.${featureName}Entity
        
        interface ${featureName}Repository {
            suspend fun get${featureName}Items(): List<${featureName}Entity>
            suspend fun save${featureName}Item(entity: ${featureName}Entity)
        }
    """.trimIndent()

    fun stringProviderInterface(packageName: String, featureName: String) = """
        package $packageName
        
        import com.davidshibru.taskflow.core.essentials.resources.StringProvider
        
        interface ${featureName}StringProvider : StringProvider {
            val defaultErrorMessage: String
        }
    """.trimIndent()

    fun stringProviderImpl(
        packageName: String,
        domainPackageName: String,
        featureName: String,
        errorStringName: String
    ) = """
        package $packageName.resources
        
        import android.content.Context
        import $domainPackageName.resources.${featureName}StringProvider
        import $packageName.R
        import dagger.hilt.android.qualifiers.ApplicationContext
        import javax.inject.Inject
        
        class ${featureName}StringProviderImpl @Inject constructor(
            @param:ApplicationContext private val context: Context,
        ) : ${featureName}StringProvider {
        
            override val defaultErrorMessage: String
                get() = context.getString(R.string.$errorStringName)
        }
    """.trimIndent()

    fun stringProviderModule(
        packageName: String,
        domainPackageName: String,
        featureName: String
    ) = """
        package $packageName.di
        
        import com.davidshibru.taskflow.core.essentials.resources.StringProvider
        import $domainPackageName.resources.${featureName}StringProvider
        import $packageName.resources.${featureName}StringProviderImpl
        import dagger.Binds
        import dagger.Module
        import dagger.hilt.InstallIn
        import dagger.hilt.components.SingletonComponent
        import dagger.multibindings.ClassKey
        import dagger.multibindings.IntoMap
        
        @Module
        @InstallIn(SingletonComponent::class)
        interface ${featureName}StringProviderModule {
        
            @Binds
            @IntoMap
            @ClassKey(${featureName}StringProvider::class)
            fun bind${featureName}StringProviderIntoMap(
                impl: ${featureName}StringProviderImpl,
            ): StringProvider
        
            @Binds
            fun bind${featureName}StringProvider(
                impl: ${featureName}StringProviderImpl,
            ): ${featureName}StringProvider
        }
    """.trimIndent()

    fun presentationStringsXml(errorStringName: String, featureName: String) = """
        <?xml version="1.0" encoding="utf-8"?>
        <resources>
            <string name="$errorStringName">Something went wrong in $featureName</string>
        </resources>
    """.trimIndent()

    // --- ШАБЛОНЫ ДЛЯ DEMO СЛОЯ ---

    fun demoRepositoryClass(
        packageName: String,
        domainPackageName: String,
        featureName: String,
        basePackage: String = "com.davidshibru.taskflow"
    ) = """
        package $packageName
        
        import com.davidshibru.taskflow.core.essentials.entities.Id
        import $domainPackageName.entities.${featureName}Entity
        import $domainPackageName.repositories.${featureName}Repository
        import javax.inject.Inject
        import javax.inject.Singleton
        
        @Singleton
        class Demo${featureName}Repository @Inject constructor() : ${featureName}Repository {
            
            private val items = mutableListOf(
                ${featureName}Entity(
                    id = Id(1),
                    title = "$featureName item",
                )
            )
        
            override suspend fun get${featureName}Items(): List<${featureName}Entity> {
                return items.toList()
            }
        
            override suspend fun save${featureName}Item(entity: ${featureName}Entity) {
                val index = items.indexOfFirst { it.id == entity.id }
                if (index == -1) {
                    items += entity
                } else {
                    items[index] = entity
                }
            }
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
