package com.davidshibru.taskflow.feature.init.domain

import com.davidshibru.taskflow.core.essentials.entities.ImageSource
import com.davidshibru.taskflow.feature.init.domain.entities.KeyFeature
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

//interface GetKeyFeatureUseCase {
//    operator fun invoke(): Flow<Container<KeyFeature>>
//}

private val keyFeatures = listOf(
    KeyFeature(
        id = 1L,
        title = "Быстрая отправка сообщений",
        description = "Сообщения теперь отправляются заметно быстрее и стабильнее.",
        imageSource = ImageSource.Remote("https://minecraft-inside.ru/uploads/nick/3d/ky_lema.png")
    ),
    KeyFeature(
        id = 2L,
        title = "Улучшенные уведомления",
        description = "Уведомления о новых сообщениях стали точнее и приходят без задержек.",
        imageSource = ImageSource.Remote("https://upload.wikimedia.org/wikipedia/en/9/90/Silk%2C_Vol._3%2C_no._1_textless_cover_%28variant%29.png")
    ),
    KeyFeature(
        id = 3L,
        title = "Обновлённый интерфейс чата",
        description = "Мы сделали экран переписки чище и удобнее.",
        imageSource = ImageSource.Remote("https://avatars.mds.yandex.net/get-znatoki/1540166/2a0000018881c74cdb7ca576020725608df9/orig")
    ),
)

class GetKeyFeatureUseCase @Inject constructor() {
    operator fun invoke(): Flow<KeyFeature> {
        return flow {
            delay(2000)
//            throw ConnectionException()
            emit(keyFeatures.random())
        }
    }
}