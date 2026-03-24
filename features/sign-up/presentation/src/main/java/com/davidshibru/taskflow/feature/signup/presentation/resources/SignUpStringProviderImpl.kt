package com.davidshibru.taskflow.feature.signup.presentation.resources

import android.content.Context
import com.davidshibru.taskflow.feature.signup.domain.entities.InputField
import com.davidshibru.taskflow.feature.signup.domain.resources.SignUpStringProvider
import com.davidshibru.taskflow.feature.signup.presentation.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SignUpStringProviderImpl @Inject constructor(
    @param:ApplicationContext private val context: Context
) : SignUpStringProvider {
    override val loginField: String
        get() = context.getString(R.string.sign_up_login)
    override val passwordField: String
        get() = context.getString(R.string.sign_up_password)
    override val repeatPasswordField: String
        get() = context.getString(R.string.sign_up_repeat_password)
    override val firstNameField: String
        get() = context.getString(R.string.sign_up_first_name)
    override val lastNameField: String
        get() = context.getString(R.string.sign_up_last_name)
    override val ageField: String
        get() = context.getString(R.string.sign_up_age)
    override val loginAlreadyExistsError: String
        get() = context.getString(R.string.sign_up_error_login_already_exists)
    override val passwordMismatchError: String
        get() = context.getString(R.string.sign_up_error_password_mismatch)

    override fun emptyFieldError(field: InputField.Text): String {
        return context.getString(R.string.sign_up_error_empty_field, field.fieldName(this))
    }

    override fun emptyFieldError(field: InputField.Number): String {
        return context.getString(R.string.sign_up_error_empty_field, field.fieldName(this))
    }

    override fun tooLongValueError(field: InputField.Text): String {
        return context.getString(R.string.sign_up_error_too_long, field.fieldName(this), field.maxChars)
    }

    override fun tooShortValueError(field: InputField.Text): String {
        return context.getString(R.string.sign_up_error_too_short, field.fieldName(this), field.minChars)
    }

    override fun invalidRangeError(field: InputField.Number): String {
        return context.getString(R.string.sign_up_error_invalid_range, field.fieldName(this), field.range.first, field.range.last)
    }
}
