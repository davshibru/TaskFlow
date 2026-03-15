package com.davidshibru.taskflow.feature.init.domain.usecases

import com.davidshibru.taskflow.feature.init.domain.ShowRandomKeyFeatureUseCase
import com.davidshibru.taskflow.feature.init.domain.entities.KeyFeature
import com.davidshibru.taskflow.feature.init.domain.entities.ShowKeyFeatureResult
import com.davidshibru.taskflow.feature.init.domain.repositories.DateTimeRepository
import com.davidshibru.taskflow.feature.init.domain.repositories.KeyFeatureRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ShowRandomKeyFeatureUseCaseImpl @Inject constructor(
    private val keyFeatureRepository: KeyFeatureRepository,
    private val dateTimeRepository: DateTimeRepository,
) : ShowRandomKeyFeatureUseCase {
    //    override fun invoke() = flow {
//        val keyFeatures = keyFeatureRepository.getKeyFeatures()
//
//        if (keyFeatures.isEmpty()) {
//            emit(ShowKeyFeatureResult.Skip)
//            return@flow
//        }
//
//        val maxDate = keyFeatures.maxOf { it.lastDisplayTime }
//        val displayPeriod = keyFeatureRepository.getDisplayPeriod()
//        val nextShowTime = maxDate.plus(displayPeriod)
//
//        val now = dateTimeRepository.now()
//
//        if (now.isBefore(nextShowTime)) {
//            emit(ShowKeyFeatureResult.Skip)
//            return@flow
//        }
//
//        val oldFeature = keyFeatures.minBy { it.lastDisplayTime }
//
//        emit(ShowKeyFeatureResult.Show(oldFeature))
//        keyFeatureRepository.saveDisplayTime(oldFeature.id, now)
//    }
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
        val now = dateTimeRepository.now()
        keyFeatureRepository.saveDisplayTime(keyFeature.id, now)
    }

    private suspend fun getRandomKeyFeature(): KeyFeature {
        val keyFeatures = keyFeatureRepository.getKeyFeatures()
        return keyFeatures.minBy { it.lastDisplayTime }
    }

    private suspend fun shouldShowKeyFeature(): Boolean {
        val period = keyFeatureRepository.getDisplayPeriod()

        val lastDisplayTime = keyFeatureRepository.getKeyFeatures().maxOf { it.lastDisplayTime }
        val now = dateTimeRepository.now()
        return lastDisplayTime + period < now
    }
}