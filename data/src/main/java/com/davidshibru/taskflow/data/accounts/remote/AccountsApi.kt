package com.davidshibru.taskflow.data.accounts.remote

import com.davidshibru.taskflow.core.essentials.container.Container
import com.davidshibru.taskflow.core.network.adapter.MapHttpCodeToException
import com.davidshibru.taskflow.data.accounts.exceptions.InvalidCredentialsDataException
import com.davidshibru.taskflow.data.accounts.exceptions.LoginIsNotAvailableDataException
import com.davidshibru.taskflow.data.accounts.remote.dto.CreateAccountRequestDto
import com.davidshibru.taskflow.data.accounts.remote.dto.CreateAccountResponseDto
import com.davidshibru.taskflow.data.accounts.remote.dto.IsLoginAvailableResponseDto
import com.davidshibru.taskflow.data.accounts.remote.dto.SetDisplayNameRequestDto
import com.davidshibru.taskflow.data.accounts.remote.dto.SignInRequestDto
import com.davidshibru.taskflow.data.accounts.remote.dto.SignInResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

internal interface AccountsApi {

    @POST("login")
    @MapHttpCodeToException(403, InvalidCredentialsDataException::class)
    suspend fun signIn(
        @Body request: SignInRequestDto,
    ): Container.Completed<SignInResponseDto>

    @GET("register/available")
    @MapHttpCodeToException(400, LoginIsNotAvailableDataException::class)
    suspend fun isLoginAvailable(
        @Query("username") login: String,
    ): Container.Completed<IsLoginAvailableResponseDto>

    @POST("register")
    @MapHttpCodeToException(400, LoginIsNotAvailableDataException::class)
    suspend fun createAccount(
        @Body body: CreateAccountRequestDto,
        @Query("kind") kind: String = "user",
    ): Container.Completed<CreateAccountResponseDto>

    @PUT("profile/{userId}/displayname")
    suspend fun setDisplayNameAfterSignUp(
        @Path("userId") userId: String,
        @Header("Authorization") authHeaderValue: String,
        @Body body: SetDisplayNameRequestDto,
    ): Container.Completed<Unit>
}