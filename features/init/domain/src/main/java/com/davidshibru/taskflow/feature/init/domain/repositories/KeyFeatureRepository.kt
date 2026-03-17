package com.davidshibru.taskflow.feature.init.domain.repositories

import com.davidshibru.taskflow.feature.init.domain.entities.KeyFeature
import java.time.Period
import java.time.ZonedDateTime

interface KeyFeatureRepository {
    suspend fun getKeyFeatures(): List<KeyFeature>

    suspend fun getDisplayPeriod(): Period

    suspend fun saveDisplayTime(feature: KeyFeature, time: ZonedDateTime)
}