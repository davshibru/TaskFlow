package com.davidshibru.auth.impl

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.davidshibru.auth.api.AuthEntry
import javax.inject.Inject

class AuthEntryImpl @Inject constructor() : AuthEntry(){
    override fun NavGraphBuilder.registry(navController: NavHostController) {
        composable(featureRoute) {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(featureRoute)
            }
        }
    }
}