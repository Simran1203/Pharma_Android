package com.pharmacy.crack.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    companion object {
        fun create(): MyApi {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(
                    GsonConverterFactory.create()
                )
                .baseUrl("http://3.13.108.238:5000/user/")
                .build()

            return retrofit.create(MyApi::class.java)
        }
    }
}