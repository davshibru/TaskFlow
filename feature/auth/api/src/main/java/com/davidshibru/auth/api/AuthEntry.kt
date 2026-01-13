package com.davidshibru.auth.api

import com.davidshibru.core.navigation.FeatureEntry

abstract class AuthEntry : FeatureEntry{

    override val featureRoute: String
        get() = "auth"

}