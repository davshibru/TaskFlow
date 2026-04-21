package com.davidshibru.taskflow.feature.init.domain.usecases

import com.davidshibru.taskflow.core.essentials.datetime.DateTimeProvider
import com.davidshibru.taskflow.feature.init.domain.ShowRandomKeyFeatureUseCase
import com.davidshibru.taskflow.feature.init.domain.entities.KeyFeature
import com.davidshibru.taskflow.feature.init.domain.entities.ShowKeyFeatureResult
import com.davidshibru.taskflow.feature.init.domain.repositories.KeyFeatureRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ShowRandomKeyFeatureUseCaseImpl @Inject constructor(
    private val keyFeatureRepository: KeyFeatureRepository,
    private val dateTimeProvider: DateTimeProvider,
) : ShowRandomKeyFeatureUseCase {

    override fun invoke(): Flow<ShowKeyFeatureResult> = flow {
        if (shouldShowKeyFeature()) {
            val keyFeature = getRandomKeyFeature()
            emit(ShowKeyFeatureResult.Show(keyFeature))
            saveDisplayTime(keyFeature)
        } else {
            emit(ShowKeyFeatureResult.Skip)
        }
    }

    private suspend fun saveDisplayTime(keyFeature: KeyFeature) {
        val now = dateTimeProvider.now()
        keyFeatureRepository.saveDisplayTime(keyFeature, now)
    }

    private suspend fun getRandomKeyFeature(): KeyFeature {
        val keyFeatures = keyFeatureRepository.getKeyFeatures()
        return keyFeatures.minBy { it.lastDisplayTime }
    }

    private suspend fun shouldShowKeyFeature(): Boolean {
        val period = keyFeatureRepository.getDisplayPeriod()

        val lastDisplayTime = keyFeatureRepository.getKeyFeatures().maxOf { it.lastDisplayTime }
        val now = dateTimeProvider.now()
        return lastDisplayTime + period < now
    }
}