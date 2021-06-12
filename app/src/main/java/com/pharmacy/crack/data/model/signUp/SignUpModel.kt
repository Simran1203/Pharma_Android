package com.pharmacy.crack.data.model.signUp

data class SignUpModel(
    val auth_token: String,
    val `data`: List<Data>,
    val message: String
)