package com.pharmacy.crack.utils
import com.pharmacy.crack.data.model.classificationModels.ClassificationModel
import com.pharmacy.crack.data.model.signUp.SignUpModel
import com.pharmacy.crack.data.model.specialityModels.SpecialityModel
import com.pharmacy.crack.data.model.statesModels.StateModel
import com.pharmacy.crack.main.model.RegisterDataModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MyApi {

    @GET("classification")
    suspend fun getClassification() : Response<ClassificationModel>

    @GET("speciality")
    suspend fun getSpeciality() : Response<SpecialityModel>

    @GET("countrystatecity")
    suspend fun getState(@Query("countryId") countryId:String) :Response<StateModel>

    @POST("register")
    suspend fun submitSignUp(@Body model: RegisterDataModel) :Response<SignUpModel>
}