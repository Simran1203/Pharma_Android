package com.pharmacy.crack.data.model.specialityModels

data class Getspeciality(
    val id: Int,
    val name: String
){
    override fun toString(): String {
        return name
    }
}