package com.pharmacy.crack.data.model.statesModels

data class State(
    val countryCode: String,
    val isoCode: String,
    val latitude: String,
    val longitude: String,
    val name: String
){
    override fun toString(): String {
        return name
    }
}