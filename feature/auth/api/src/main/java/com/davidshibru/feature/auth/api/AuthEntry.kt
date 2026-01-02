package com.davidshibru.feature.auth.api

import com.davidshibru.taskflow.core.navigation.FeatureEntry

abstract class AuthEntry : FeatureEntry{

    override val featureRoute: String = "auth"

}