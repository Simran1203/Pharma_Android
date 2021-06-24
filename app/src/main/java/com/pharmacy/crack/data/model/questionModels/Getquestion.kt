package com.pharmacy.crack.data.model.questionModels

data class Getquestion(
    val category_id: Int,
    val category_name: String,
    val correct_option: Int,
    val correct_points: Int,
    val id: Int,
    val level_no: Int,
    val option_1: String,
    val option_2: String,
    val option_3: String,
    val option_4: String,
    val question: String
){
    override fun toString(): String {
        return question
    }
}