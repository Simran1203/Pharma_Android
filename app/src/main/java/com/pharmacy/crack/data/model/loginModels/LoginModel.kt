package com.pharmacy.crack.data.model.loginModels

data class LoginModel(
    val `data`: LoginResponse,
    val message: String,
    val auth_token: String
)