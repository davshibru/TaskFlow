package com.davidshibru.taskflow.core.data.network.dto

import com.davidshibru.taskflow.core.essentials.entities.Id
import com.davidshibru.taskflow.core.essentials.entities.UserId
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.serializer
import kotlin.reflect.KClass

interface BasicEventDto {
    val userId: UserId
}

@Serializable
internal data class BasicEventDtoImpl(
    val type: String,
    @Serializable(UserIdSerializer::class) override val userId: UserId,
) : BasicEventDto

@Serializable(EventDtoSerializer::class)
data class EventDto<T>(
    val basicEvent: BasicEventDto,
    val content: T,
) : BasicEventDto by basicEvent

@Serializable
sealed class ContentDto {

    @Serializable
    data class Member(
        val displayname: String?,
    ) : ContentDto()

    @Serializable
    data class Message(
        val body: String,
    ) : ContentDto()

    @Serializable
    data object Unknown : ContentDto()
}


class IdSerializer<T : Id>(
    idClass: KClass<T>,
    private val idFactory: (String) -> T,
) : KSerializer<T> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        serialName = requireNotNull(idClass.qualifiedName),
        kind = PrimitiveKind.STRING,
    )

    override fun serialize(encoder: Encoder, value: T) = error("Serialization is not supported")
    override fun deserialize(decoder: Decoder): T = idFactory(decoder.decodeString())
}

object UserIdSerializer : KSerializer<UserId> by IdSerializer(
    idClass = UserId::class,
    idFactory = UserId.Companion::invoke
)

class EventDtoSerializer<T : ContentDto>(
    contentSerializer: KSerializer<T>
) : KSerializer<EventDto<T>> {

    private val serializers = mapOf(
        "m.room.message" to serializer<ContentDto.Message>(),
        "m.room.member" to serializer<ContentDto.Member>(),
    )

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor(
        serialName = requireNotNull(EventDto::class.qualifiedName),
        contentSerializer.descriptor,
    )

    override fun serialize(
        encoder: Encoder,
        value: EventDto<T>
    ) = error("Serialization is not supported")

    override fun deserialize(decoder: Decoder): EventDto<T> {
        decoder as JsonDecoder
        val json = decoder.json
        val jsonObject = decoder.decodeJsonElement().jsonObject

        val basicEvent = json.decodeFromJsonElement<BasicEventDtoImpl>(jsonObject)

        val jsonContent = requireNotNull(jsonObject["content"])

        val content = serializers[basicEvent.type]
            ?.let { deserializer -> json.decodeFromJsonElement(deserializer, jsonContent) }
            ?: ContentDto.Unknown

        return EventDto(
            basicEvent = basicEvent,
            content = content as T,
        )
    }
}

