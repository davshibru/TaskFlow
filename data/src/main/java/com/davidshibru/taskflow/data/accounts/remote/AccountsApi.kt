package com.davidshibru.taskflow.data.accounts.remote

import com.davidshibru.taskflow.data.accounts.remote.dto.SignInRequestDto
import com.davidshibru.taskflow.data.accounts.remote.dto.SignInResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AccountsApi {

    @POST
    suspend fun signIn(
        @Body request: SignInRequestDto,
    ): SignInResponseDto

}