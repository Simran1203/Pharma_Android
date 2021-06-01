package com.pharmacy.crack.main.model

data class RegisterDataModel(
    val full_name: String,
    val email_address: String,
    val password: String,
    val username: String,
    val dob: String,
    val country: String,
    val state: String,
    val clasification_id: Int,
    val speciality_id: Int,
    val college: String,
)
