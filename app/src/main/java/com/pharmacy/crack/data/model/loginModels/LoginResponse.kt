package com.pharmacy.crack.data.model.loginModels

data class LoginResponse(
    val auth_token: String,
    val clasification_id: Int,
    val college: String,
    val country: String,
    val dob: String,
    val email_address: String,
    val full_name: String,
    val id: Int,
    val password: String,
    val reset_password_code: Any,
    val speciality_id: Int,
    val state: String,
    val username: String
)