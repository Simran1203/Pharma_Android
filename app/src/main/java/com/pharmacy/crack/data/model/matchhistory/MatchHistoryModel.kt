package com.pharmacy.crack.data.model.matchhistory

data class MatchHistoryModel(
    val lossmatch: List<Lossmatch>,
    val message: String,
    val winmatch: List<Winmatch>
)