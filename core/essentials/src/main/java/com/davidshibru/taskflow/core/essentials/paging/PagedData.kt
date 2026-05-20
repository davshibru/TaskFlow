package com.davidshibru.taskflow.core.essentials.paging

interface PagedData<T> {

    val data: List<T>
    val next: PageToken?

}