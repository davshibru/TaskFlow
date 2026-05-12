package com.davidshibru.taskflow.core.navigation.base.impl

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.hilt.lifecycle.viewmodel.HiltViewModelFactory
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.davidshibru.taskflow.core.navigation.dsl.ScreenNavigationBar
import com.davidshibru.taskflow.core.navigation.dsl.ScreenScope
import com.davidshibru.taskflow.core.navigation.dsl.ScreenToolbar
import dagger.hilt.android.lifecycle.withCreationCallback
import kotlinx.coroutines.CoroutineScope
import kotlin.reflect.KClass

internal class Nav3ScreenScope(
    override val context: Context,
    override val coroutineScope: CoroutineScope,
    private val viewModelStoreOwner: ViewModelStoreOwner,
    private val defaultsProvider: HasDefaultViewModelProviderFactory,
) : ScreenScope,
    ViewModelStoreOwner by viewModelStoreOwner,
    HasDefaultViewModelProviderFactory by defaultsProvider {

    override var toolbar: ScreenToolbar by mutableStateOf(ScreenToolbar.Hidden)

    override var navigationBar: ScreenNavigationBar by  mutableStateOf(ScreenNavigationBar.Hidden)

    private var content: @Composable () -> Unit by mutableStateOf({})

    override fun content(block: @Composable (() -> Unit)) {
        this.content = block
    }

    @Composable
    fun Content() {
        this.content()
    }

    override fun <T : ViewModel> viewModel(vmClass: KClass<T>): T {
        return getViewModel<T, Nothing>(vmClass)
    }

    override fun <T : ViewModel, F> viewModel(
        vmClass: KClass<T>,
        callback: F.() -> T
    ): T {
        return getViewModel(vmClass, callback)
    }

    private fun <T: ViewModel, F> getViewModel(
        vmClass: KClass<T>,
        callback: (F.() -> T)? = null
    ): T{
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
}