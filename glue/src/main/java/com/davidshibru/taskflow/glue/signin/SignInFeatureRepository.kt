package com.davidshibru.taskflow.glue.signin

import com.davidshibru.taskflow.core.essentials.container.map
import com.davidshibru.taskflow.core.essentials.container.mapException
import com.davidshibru.taskflow.data.AccountsDataRepository
import com.davidshibru.taskflow.data.accounts.exceptions.InvalidCredentialsDataException
import com.davidshibru.taskflow.features.signin.domain.entities.Credentials
import com.davidshibru.taskflow.features.signin.domain.entities.Token
import com.davidshibru.taskflow.features.signin.domain.exceptions.InvalidCredentialsException
import com.davidshibru.taskflow.features.signin.domain.repositories.SignInRepository
import com.davidshibru.taskflow.glue.signin.mapper.MapperCredentials
import com.davidshibru.taskflow.glue.signin.mapper.MapperToken
import javax.inject.Inject

internal class SignInFeatureRepository @Inject constructor(
    private val mapperToken: MapperToken,
    private val mapperCredentials: MapperCredentials,
    private val accountsDataRepository: AccountsDataRepository,
) : SignInRepository {
    override suspend fun signIn(credentials: Credentials): Token {
        return accountsDataRepository
            .signIn(mapperCredentials.toAuthDataCredentials(credentials))
            .map { mapperToken.toToken(it) }
            .mapException(InvalidCredentialsDataException::class, ::InvalidCredentialsException)
            .unwrap()
    }
}
