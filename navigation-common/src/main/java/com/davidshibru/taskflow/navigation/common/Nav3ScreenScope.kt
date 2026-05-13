package com.davidshibru.taskflow.navigation.common

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.hilt.lifecycle.viewmodel.HiltViewModelFactory
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation3.runtime.NavKey
import com.davidshibru.taskflow.core.navigation.dsl.BaseRoute
import com.davidshibru.taskflow.core.navigation.dsl.ScreenBackHandler
import com.davidshibru.taskflow.core.navigation.dsl.ScreenNavigationBar
import com.davidshibru.taskflow.core.navigation.dsl.ScreenScope
import com.davidshibru.taskflow.core.navigation.dsl.ScreenToolbar
import com.davidshibru.taskflow.core.navigation.dsl.viewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.lifecycle.withCreationCallback
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import kotlin.reflect.KClass

internal class Nav3ScreenScope(
    override val context: Context,
    override val coroutineScope: CoroutineScope,
    private val screenConfigurationStore: ScreenConfigurationStore,
    private val viewModelStoreOwner: ViewModelStoreOwner,
    private val defaultsProvider: HasDefaultViewModelProviderFactory,
) : ScreenScope,
    ViewModelStoreOwner by viewModelStoreOwner,
    HasDefaultViewModelProviderFactory by defaultsProvider {

    override val savedStateHandle: SavedStateHandle
        get() = viewModel<SavedStateHandleViewModel>().savedStateHandle

    override var toolbar: ScreenToolbar by mutableStateOf(ScreenToolbar.Hidden)

    override var navigationBar: ScreenNavigationBar by mutableStateOf(ScreenNavigationBar.Hidden)

    override var backHandler: ScreenBackHandler by mutableStateOf(ScreenBackHandler.Default)

    private var content: @Composable () -> Unit by mutableStateOf({})

    override fun content(block: @Composable () -> Unit) {
        content = block
    }

    @Composable
    fun Content() {
        content()
    }

    override fun <T : ViewModel> viewModel(vmClass: KClass<T>): T {
        return getViewModel<T, Nothing>(vmClass)
    }

    override fun <T : ViewModel, F> viewModel(
        vmClass: KClass<T>,
        callback: F.() -> T,
    ): T {
        return getViewModel(vmClass, callback)
    }

    override fun applyConfiguration(
        route: BaseRoute,
        scope: ScreenScope
    ) {
        val configuration = screenConfigurationStore.getConfiguration(route)
        configuration.invoke(scope, route)
    }

    private fun <T : ViewModel, F> getViewModel(
        vmClass: KClass<T>,
        callback: (F.() -> T)? = null,
    ): T {
        val factory = HiltViewModelFactory(context, defaultViewModelProviderFactory)
        val extras = defaultViewModelCreationExtras
        val finalExtras = if (callback == null) {
            extras
        } else {
            extras.withCreationCallback(callback)
        }
        val provider = ViewModelProvider.create(viewModelStoreOwner, factory, finalExtras)
        return provider[vmClass]
    }

    @HiltViewModel
    internal class SavedStateHandleViewModel @Inject constructor(
        val savedStateHandle: SavedStateHandle,
    ) : ViewModel()
}
