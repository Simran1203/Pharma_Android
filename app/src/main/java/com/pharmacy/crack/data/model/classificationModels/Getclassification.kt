package com.pharmacy.crack.data.model.classificationModels

data class Getclassification(
    val id: Int,
    val name: String
){
    override fun toString(): String {
        return name
    }
}