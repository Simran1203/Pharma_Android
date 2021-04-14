package com.pharmacy.crack.utils

import com.pharmacy.crack.main.model.countryModel.CountryMainModel
import com.pharmacy.crack.main.model.stateModel.StateMainModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface MyApi {

    @GET
    suspend fun getCountry(@Url url: String?) : Response<CountryMainModel>

    @GET("childrenJSON")
    suspend fun getState(@Query("geonameId") query: Int,@Query("username") usewrname: String) : Response<StateMainModel>
}