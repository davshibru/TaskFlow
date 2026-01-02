package com.davidshibru.feature.auth.impl

import androidx.compose.material3.Text
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.davidshibru.feature.auth.api.AuthEntry
import javax.inject.Inject

class AuthEntryImpl @Inject constructor() : AuthEntry() {
    override fun NavGraphBuilder.register(navController: NavHostController) {
        composable(featureRoute){
            Text("Auth")
        }
    }
}