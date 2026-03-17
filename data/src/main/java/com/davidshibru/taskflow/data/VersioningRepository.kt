package com.davidshibru.taskflow.data

import com.davidshibru.taskflow.core.essentials.entities.Id
import com.davidshibru.taskflow.data.versioning.entities.KeyFeatureDataEntity
import java.time.Period
import java.time.ZonedDateTime

interface VersioningRepository {

    suspend fun getKeyFeatures(): List<KeyFeatureDataEntity>

    suspend fun getDisplayPeriod() : Period

    suspend fun saveLastDisplayTime(keyFeatureId: Id, time: ZonedDateTime)

    suspend fun getLastDisplayTime(keyFeatureId: Id): ZonedDateTime

}