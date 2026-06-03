package com.davidshibru.taskflow.core.data.paging

import com.davidshibru.taskflow.core.data.network.dto.ChunkDto
import com.davidshibru.taskflow.core.data.network.dto.ContentDto
import com.davidshibru.taskflow.core.data.network.dto.EventDto
import com.davidshibru.taskflow.core.data.network.dto.PageTokenQueryParam
import com.davidshibru.taskflow.core.essentials.container.Container.Completed
import com.davidshibru.taskflow.core.essentials.paging.PageToken
import javax.inject.Inject
import kotlin.reflect.KClass

suspend inline fun <reified T : Any> PagingUtils.firstEventOfType(
    noinline fetcher: suspend (PageTokenQueryParam?) -> Completed<ChunkDto<EventDto<ContentDto>>>,
): T? {
    return firstEventOfType(
        kClass = T::class,
        fetcher = fetcher,
    )
}


interface PagingUtils {

    suspend fun <T : Any> firstEventOfType(
        kClass: KClass<T>,
        fetcher: suspend (PageTokenQueryParam?) -> Completed<ChunkDto<EventDto<ContentDto>>>,
    ): T?

}

internal class PagingUtilsImpl @Inject constructor() : PagingUtils {

    override suspend fun <T : Any> firstEventOfType(
        kClass: KClass<T>,
        fetcher: suspend (PageTokenQueryParam?) -> Completed<ChunkDto<EventDto<ContentDto>>>
    ): T? {
        var content: T?
        var pageKey: PageToken? = null

        do {
            val pageTokenQueryParam = pageKey?.let(::PageTokenQueryParam)
            val eventResponse = fetcher(pageTokenQueryParam).unwrap()

            pageKey = eventResponse.next

            content = eventResponse
                .data
                .firstOrNull { event -> event.content::class == kClass }
                ?.content as? T

        } while (content == null && pageKey != null)

        return content
    }
}