package com.davidshibru.taskflow.core.data.network.dto

import com.davidshibru.taskflow.core.essentials.paging.PageToken
import com.davidshibru.taskflow.core.essentials.paging.PagedData
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class ChunkDto<T>(
    private val chunk: List<T>,
    private val start: String?,
    private val end: String?,
): PagedData<T> {

    @Transient
    override val data: List<T> = chunk

    @Transient
    override val next: PageToken? = end?.let(::PageTokenImpl)

    private data class PageTokenImpl(
        override val value: String,
    ): PageToken
}
