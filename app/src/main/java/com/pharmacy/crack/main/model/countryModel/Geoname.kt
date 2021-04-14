package com.pharmacy.crack.main.model.countryModel

data class Geoname(
    val areaInSqKm: String,
    val capital: String,
    val continent: String,
    val continentName: String,
    val countryCode: String,
    val countryName: String,
    val currencyCode: String,
    val east: Double,
    val fipsCode: String,
    val geonameId: Int,
    val isoAlpha3: String,
    val isoNumeric: String,
    val languages: String,
    val north: Double,
    val population: String,
    val postalCodeFormat: String,
    val south: Double,
    val west: Double
)