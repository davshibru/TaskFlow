package com.davidshibru.taskflow.data.accounts.remote

import com.davidshibru.taskflow.core.essentials.container.Container
import com.davidshibru.taskflow.core.network.adapter.MapHttpCodeToException
import com.davidshibru.taskflow.data.accounts.exceptions.InvalidCredentialsDataException
import com.davidshibru.taskflow.data.accounts.remote.dto.SignInRequestDto
import com.davidshibru.taskflow.data.accounts.remote.dto.SignInResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

internal interface AccountsApi {

    @POST("login")
    @MapHttpCodeToException(403, InvalidCredentialsDataException::class)
    suspend fun signIn(
        @Body request: SignInRequestDto,
    ): Container.Completed<SignInResponseDto>

}