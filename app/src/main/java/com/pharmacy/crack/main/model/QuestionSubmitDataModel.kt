package com.pharmacy.crack.main.model

data class QuestionSubmitDataModel(val level_no:String,
                                   val question:String,
                                   val category_id:String,
                                   val option_1:String,
                                   val option_2:String,
                                   val option_3:String,
                                   val option_4:String,
                                   val correct_points:String,
)
