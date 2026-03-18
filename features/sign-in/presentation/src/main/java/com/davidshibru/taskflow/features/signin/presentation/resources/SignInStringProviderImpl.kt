package com.davidshibru.taskflow.features.signin.presentation.resources

import android.content.Context
import com.davidshibru.taskflow.features.signin.domain.entities.InputField
import com.davidshibru.taskflow.features.signin.domain.resources.SignInStringProvider
import com.davidshibru.taskflow.features.signin.presentation.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SignInStringProviderImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : SignInStringProvider{
    override val loginFieldName: String = context.getString(R.string.login)
    override val passwordFieldName: String = context.getString(R.string.password)

    override val invalidCredentialsError: String = context.getString(R.string.invalid_login_and_or_password)

    override fun emptyFieldError(field: InputField): String {
        val fieldName = field.fieldName(this)
        return context.getString(R.string.empty_field_error, fieldName )
    }
}