package com.davidshibru.taskflow.core.data.network.dto

enum class DirectionQueryParam(val serializedValue: String) {

    Forward("f"),
    Backward("b");

    override fun toString(): String = serializedValue
}