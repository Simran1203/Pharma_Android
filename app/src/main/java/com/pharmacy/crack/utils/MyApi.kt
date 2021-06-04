package com.pharmacy.crack.utils
import com.pharmacy.crack.data.model.categoryModels.CategoryModel
import com.pharmacy.crack.data.model.classificationModels.ClassificationModel
import com.pharmacy.crack.data.model.forgetpassowrods.ForgetModel
import com.pharmacy.crack.data.model.loginModels.LoginModel
import com.pharmacy.crack.data.model.signUp.SignUpModel
import com.pharmacy.crack.data.model.specialityModels.SpecialityModel
import com.pharmacy.crack.data.model.statesModels.StateModel
import com.pharmacy.crack.main.model.*
import retrofit2.Response
import retrofit2.http.*

interface MyApi {

    @GET("classification")
    suspend fun getClassification() : Response<ClassificationModel>

    @GET("speciality")
    suspend fun getSpeciality() : Response<SpecialityModel>

    @GET("countrystatecity")
    suspend fun getState(@Query("countryId") countryId:String) :Response<StateModel>

    @GET("category")
    suspend fun getcategory() :Response<CategoryModel>

    @POST("register")
    suspend fun submitSignUp(@Body model: RegisterDataModel) :Response<SignUpModel>

    @POST("login")
    suspend fun submitLogin(@Body model: LoginDatamodel) :Response<LoginModel>

    @POST("resetcode")
    suspend fun submitResetCode(@Body model: ResetCodeDataModel) :Response<ForgetModel>

    @POST("forgot-password")
    suspend fun submitForget(@Body model: ForgetDataModel) :Response<ForgetModel>

    @POST("newpassword")
    suspend fun submitNewPassword(@Body model: LoginDatamodel) :Response<ForgetModel>
}