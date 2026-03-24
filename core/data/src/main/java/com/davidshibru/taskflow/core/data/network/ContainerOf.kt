package com.davidshibru.taskflow.core.data.network

import com.davidshibru.taskflow.core.data.network.conventer.errorJson
import com.davidshibru.taskflow.core.data.network.dto.ErrorDto
import com.davidshibru.taskflow.core.essentials.container.Container
import com.davidshibru.taskflow.core.essentials.container.Container.Loading.errorContainer
import com.davidshibru.taskflow.core.essentials.container.Container.Loading.successContainer
import com.davidshibru.taskflow.core.essentials.exception.BackendException
import com.davidshibru.taskflow.core.essentials.exception.ConnectionException
import com.davidshibru.taskflow.core.essentials.exception.InvalidBackendResponseException
import com.davidshibru.taskflow.core.essentials.exception.UnknownException
import kotlinx.serialization.SerializationException
import okio.IOException
import retrofit2.HttpException
import java.lang.Exception

inline fun <T> containerOf(block: () -> T): Container.Completed<T> {
    return try {
        successContainer(block())
    } catch (e: HttpException) {
        val code = e.code()
        val message = try {
            e.response()?.errorBody()?.string()
                ?.let { errorJson.decodeFromString<ErrorDto>(it) }
                ?.error
                ?: e.message()
        } catch (e: Exception) {
            return errorContainer(InvalidBackendResponseException(e))
        }
        errorContainer(BackendException(code, message))
    } catch (e: SerializationException) {
        errorContainer(InvalidBackendResponseException(e))
    } catch (e: IOException) {
        errorContainer(ConnectionException(e))
    } catch (e: Exception) {
        errorContainer(UnknownException(e))
    }
}