package com.davidshibru.taskflow.feature.init.domain

import com.davidshibru.taskflow.feature.init.domain.entities.KeyFeature
import kotlinx.coroutines.flow.Flow

interface GetKeyFeatureUseCase {
    operator fun invoke(): Flow<KeyFeature>
}



//class GetKeyFeatureUseCase @Inject constructor() {
//    operator fun invoke(): Flow<KeyFeature> {
//        return flow {
//            delay(2000)
////            throw ConnectionException()
//            emit(keyFeatures.random())
//        }
//    }
//}