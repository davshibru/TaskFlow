package com.davidshibru.taskflow.core.data.network.dto

import com.davidshibru.taskflow.core.essentials.paging.PageToken

class PageTokenQueryParam(
    val pageToken: PageToken
) : PageToken by pageToken {

    override fun toString() = pageToken.value
}