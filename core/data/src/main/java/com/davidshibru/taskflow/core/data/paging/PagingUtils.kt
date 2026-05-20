package com.davidshibru.taskflow.core.data.paging

import com.davidshibru.taskflow.core.data.network.dto.ChunkDto
import com.davidshibru.taskflow.core.data.network.dto.EventDto
import com.davidshibru.taskflow.core.data.network.dto.EventType
import com.davidshibru.taskflow.core.data.network.dto.PageTokenQueryParam
import com.davidshibru.taskflow.core.essentials.container.Container.Completed
import com.davidshibru.taskflow.core.essentials.paging.PageToken
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.serializer
import javax.inject.Inject

interface PagingUtils {
    suspend fun <T : Any> firstEventOfType(
        eventType: EventType<T>,
        deserializer: (Json) -> DeserializationStrategy<T>,
        fetcher: suspend (PageTokenQueryParam?) -> Completed<ChunkDto<EventDto<JsonElement>>>,
    ): T?
}


suspend inline fun <reified T : Any> PagingUtils.firstEventOfType(
    eventType: EventType<T>,
    noinline fetcher: suspend (PageTokenQueryParam?) -> Completed<ChunkDto<EventDto<JsonElement>>>,
): T? {
    return firstEventOfType(
        eventType = eventType,
        deserializer = { json -> json.serializersModule.serializer<T>() },
        fetcher = fetcher,
    )
}

internal class PagingUtilsImpl @Inject constructor(
    private val json: Json,
) : PagingUtils {
    override suspend fun <T : Any> firstEventOfType(
        eventType: EventType<T>,
        deserializer: (Json) -> DeserializationStrategy<T>,
        fetcher: suspend (PageTokenQueryParam?) -> Completed<ChunkDto<EventDto<JsonElement>>>
    ): T? {
        var content: T?
        var pageKey: PageToken? = null

        do {
            val pageTokenQueryParam = pageKey?.let(::PageTokenQueryParam)
            val eventResponse = fetcher(pageTokenQueryParam).unwrap()

            pageKey = eventResponse.next

            content = eventResponse
                .data
                .firstOrNull { event -> event.type == eventType.serializedTypeName }
                ?.content
                ?.let { element ->
                    json.decodeFromJsonElement(deserializer = deserializer(json), element = element)
                }

        } while (content == null && pageKey != null)

        return content
    }
}