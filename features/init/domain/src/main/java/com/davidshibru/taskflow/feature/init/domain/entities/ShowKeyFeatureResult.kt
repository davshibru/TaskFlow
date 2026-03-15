package com.davidshibru.taskflow.feature.init.domain.entities

sealed interface ShowKeyFeatureResult {

    data class Show(val keyFeature: KeyFeature) : ShowKeyFeatureResult

    object Skip : ShowKeyFeatureResult

}