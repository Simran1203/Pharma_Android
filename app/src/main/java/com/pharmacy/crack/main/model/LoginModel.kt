package com.pharmacy.crack.main.model

import android.util.Patterns
import kotlinx.android.synthetic.main.activity_login.*


class LoginModel(private val strEmailAddress: String, private val strPassword: String) {
    val isEmailEmpty: Boolean
        get() =  strEmailAddress.trim().isEmpty()

    val isEmailValid: Boolean
        get() = Patterns.EMAIL_ADDRESS.matcher(strEmailAddress).matches()

    val isPasswordEmpty: Boolean
        get() = strPassword.trim().isEmpty()

    val isPasswordLengthGreaterThan5: Boolean
        get() = strPassword.length > 5

}