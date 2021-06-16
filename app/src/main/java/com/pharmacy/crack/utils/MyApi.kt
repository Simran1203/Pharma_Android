package com.pharmacy.crack.utils
import com.pharmacy.crack.data.model.EmailSupportModel
import com.pharmacy.crack.data.model.categoryModels.CategoryModel
import com.pharmacy.crack.data.model.classificationModels.ClassificationModel
import com.pharmacy.crack.data.model.forgetpassowrods.ForgetModel
import com.pharmacy.crack.data.model.loginModels.LoginModel
import com.pharmacy.crack.data.model.signUp.SignUpModel
import com.pharmacy.crack.data.model.specialityModels.SpecialityModel
import com.pharmacy.crack.data.model.statesModels.StateModel
import com.pharmacy.crack.main.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface MyApi {

    @GET("masterClassification")
    suspend fun getClassification() : Response<ClassificationModel>

    @GET("masterSpeciality")
    suspend fun getSpeciality() : Response<SpecialityModel>

    @GET("countrystatecity")
    suspend fun getState(@Query("countryId") countryId:String) :Response<StateModel>

    @GET("masterCategory")
    suspend fun getcategory(@Header("Authorization") token: String) :Response<CategoryModel>

    @POST("user/register")
    suspend fun submitSignUp(@Body model: RegisterDataModel) :Response<SignUpModel>

    @POST("user/login")
    suspend fun submitLogin(@Body model: LoginDatamodel) :Response<LoginModel>

    @POST("user/checkResetCode")
    suspend fun submitResetCode(@Body model: ResetCodeDataModel) :Response<ForgetModel>

    @POST("user/forgotPassword")
    suspend fun submitForget(@Body model: ForgetDataModel) :Response<ForgetModel>

    @POST("user/setNewPassword")
    suspend fun submitNewPassword(@Body model: LoginDatamodel) :Response<ForgetModel>

    @POST("contactqueries")
    suspend fun submitQueries(@Header("Authorization") token: String,@Body model: EmailsupportDataModel) :Response<EmailSupportModel>

    @Multipart
    @POST("user/profile")
    suspend fun submitResetUserName(@Part("current_username") current_username: RequestBody,@Part("new_username") new_username: RequestBody, @Part file: MultipartBody.Part) :Response<EmailSupportModel>
}