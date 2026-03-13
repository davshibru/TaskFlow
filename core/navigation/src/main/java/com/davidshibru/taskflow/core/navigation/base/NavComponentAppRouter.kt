package com.davidshibru.taskflow.core.navigation.base

import androidx.navigation.NavController
import com.davidshibru.taskflow.core.navigation.Route
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class NavComponentAppRouter @Inject constructor() : AppRouter {

    private var navController: NavController? = null
    private val commands = mutableListOf<NavController.() -> Unit>()

    override fun launch(route: Route) = execute { navController ->
        navController.navigate(route)
    }

    override fun restart(route: Route) = execute { navController ->
        navController.navigate(route) {
            popUpTo(navController.graph.id) {
                inclusive = true
            }
        }
    }

    override fun goBack() = execute { navController ->
        navController.navigateUp()
    }

    fun setNavController(navController: NavController?) {
        this.navController = navController
        if (navController != null) {
            commands.toList().forEach { command ->
                command.invoke(navController)
            }
            commands.clear()
        }
    }

    private fun execute(command: (NavController) -> Unit) {
        val navController = this.navController

        if (navController != null) {
            command.invoke(navController)
        } else {
            commands.add(command)
        }
    }
}