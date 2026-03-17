package com.davidshibru.taskflow.glue.init

import com.davidshibru.taskflow.data.VersioningRepository
import com.davidshibru.taskflow.feature.init.domain.entities.KeyFeature
import com.davidshibru.taskflow.feature.init.domain.repositories.KeyFeatureRepository
import com.davidshibru.taskflow.glue.init.mappers.KeyFeatureMapper
import java.time.Period
import java.time.ZonedDateTime
import javax.inject.Inject

class InitKeyFeatureRepository @Inject constructor(
    private var keyFeatureMapper: KeyFeatureMapper,
    private val versioningRepository: VersioningRepository,
) : KeyFeatureRepository {
    override suspend fun getKeyFeatures(): List<KeyFeature> {
        return versioningRepository.getKeyFeatures().map {
            keyFeatureMapper.toFeatureEntity(it)
        }
    }

    override suspend fun getDisplayPeriod(): Period {
        return versioningRepository.getDisplayPeriod()
    }

    override suspend fun saveDisplayTime(
        feature: KeyFeature,
        time: ZonedDateTime
    ) {
        versioningRepository.saveLastDisplayTime(feature.id, time)
    }
}