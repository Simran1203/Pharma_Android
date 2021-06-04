package com.pharmacy.crack.data.model.categoryModels

data class Category(
    val color: String,
    val id: Int,
    val name: String
){
    override fun toString(): String {
        return name
    }
}