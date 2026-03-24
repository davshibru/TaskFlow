package com.davidshibru.taskflow.glue.init.mappers

import com.davidshibru.taskflow.data.VersioningRepository
import com.davidshibru.taskflow.data.versioning.entities.KeyFeatureDataEntity
import com.davidshibru.taskflow.feature.init.domain.entities.KeyFeature
import javax.inject.Inject

interface KeyFeatureMapper {
    suspend fun toFeatureEntity(dataEntity: KeyFeatureDataEntity): KeyFeature

    class Default @Inject constructor(
        private var versioningRepository: VersioningRepository,
    ) : KeyFeatureMapper {
        override suspend fun toFeatureEntity(dataEntity: KeyFeatureDataEntity): KeyFeature {
            return with(dataEntity) {
                KeyFeature(
                    id = id,
                    title = title,
                    description = description,
                    imageSource = imageSource,
                    lastDisplayTime = versioningRepository.getLastDisplayTime(id),
                )
            }
        }
    }
}