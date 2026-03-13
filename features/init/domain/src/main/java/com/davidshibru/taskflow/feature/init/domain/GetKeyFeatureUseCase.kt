package com.davidshibru.taskflow.feature.init.domain

import com.davidshibru.taskflow.feature.init.domain.entities.KeyFeature
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

//interface GetKeyFeatureUseCase {
//    operator fun invoke(): Flow<Container<KeyFeature>>
//}

class GetKeyFeatureUseCase @Inject constructor() {
    operator fun invoke(): Flow<KeyFeature> {
        return flow {
            delay(2000)
//            throw ConnectionException()
            emit(
                KeyFeature(
                    id = 1,
                    title = "Выбранный нами инновационный путь бодрит",
                    description = "В целом, конечно, внедрение современных методик является качественно новой ступенью анализа существующих паттернов поведения.",
                )
            )
        }
    }
}