package com.davidshibru.taskflow.feature.init.domain

import com.davidshibru.taskflow.core.essentials.Container
import com.davidshibru.taskflow.feature.init.domain.entities.KeyFeature
import com.elveum.container.subject.ContainerConfiguration
import com.elveum.container.subject.LazyFlowSubject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

//interface GetKeyFeatureUseCase {
//    fun invoke(): Flow<Container<KeyFeature>>
//}

class GetKeyFeatureUseCase @Inject constructor() {
    fun invoke(): Flow<Container<KeyFeature>> {
        return flow {
            delay(2000)
//            throw ConnectionException()
            emit(
                Container.Success(
                    KeyFeature(
                        id = 1,
                        title = "This is a title",
                        description = "Tgds fgd gtt asdo kfodgk gpkth hgffop dfgje asdg vcxjf sdfafsdg gtgtgtl fdlgk",
                    )
                )
            )
        }
    }
    fun invoke(a: String) = LazyFlowSubject.create {
        delay(2000L)
        emit(KeyFeature(
            id = 1,
            title = "This is a title",
            description = "Tgds fgd gtt asdo kfodgk gpkth hgffop dfgje asdg vcxjf sdfafsdg gtgtgtl fdlgk",
        ))
    }.listen(ContainerConfiguration(emitReloadFunction = true))
}