package com.davidshibru.taskflow.feature.init.domain

import com.davidshibru.taskflow.feature.init.domain.entities.ShowKeyFeatureResult
import kotlinx.coroutines.flow.Flow

interface ShowRandomKeyFeatureUseCase {

    fun invoke(): Flow<ShowKeyFeatureResult>
}