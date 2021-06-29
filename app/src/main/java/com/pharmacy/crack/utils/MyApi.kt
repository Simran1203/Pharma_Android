package com.pharmacy.crack.utils

import com.pharmacy.crack.data.model.EmailSupportModel
import com.pharmacy.crack.data.model.categoryModels.CategoryModel
import com.pharmacy.crack.data.model.classificationModels.ClassificationModel
import com.pharmacy.crack.data.model.forgetpassowrods.ForgetModel
import com.pharmacy.crack.data.model.loginModels.LoginModel
import com.pharmacy.crack.data.model.masterBonus.DailyBonusModel
import com.pharmacy.crack.data.model.matchhistory.MatchHistoryModel
import com.pharmacy.crack.data.model.questionModels.QuestionModel
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
    suspend fun getClassification(): Response<ClassificationModel>

    @GET("masterSpeciality")
    suspend fun getSpeciality(): Response<SpecialityModel>

    @GET("countrystatecity")
    suspend fun getState(@Query("countryId") countryId: String): Response<StateModel>

    @GET("masterCategory")
    suspend fun getcategory(@Header("Authorization") token: String): Response<CategoryModel>

    @POST("user/register")
    suspend fun submitSignUp(@Body model: RegisterDataModel): Response<SignUpModel>

    @POST("user/socialRegister")
    suspend fun submitSignUpSocial(@Body model: SocialRegisterDataModel): Response<SignUpModel>

    @POST("user/login")
    suspend fun submitLogin(@Body model: LoginDatamodel): Response<LoginModel>

    @POST("user/checkResetCode")
    suspend fun submitResetCode(@Body model: ResetCodeDataModel): Response<ForgetModel>

    @POST("user/forgotPassword")
    suspend fun submitForget(@Body model: ForgetDataModel): Response<ForgetModel>

    @POST("user/setNewPassword")
    suspend fun submitNewPassword(@Body model: LoginDatamodel): Response<ForgetModel>

    @POST("user/contactqueries")
    suspend fun submitQueries(
        @Header("Authorization") token: String,
        @Body model: EmailsupportDataModel
    ): Response<EmailSupportModel>

    @Multipart
    @POST("user/profile")
    suspend fun submitResetUserNameProfile(
        @Header("Authorization") token: String,
        @Part("new_username") new_username: RequestBody,
        @Part file: MultipartBody.Part
    ): Response<EmailSupportModel>

    @Multipart
    @POST("user/profile")
    suspend fun submitResetUserProfile(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part
    ): Response<EmailSupportModel>

    @FormUrlEncoded
    @POST("user/profile")
    suspend fun submitResetUserName(
        @Header("Authorization") token: String,
        @Field("new_username") new_username: String,
    ): Response<EmailSupportModel>

    @POST("user/socialLogin")
    suspend fun submitSocialLogin(@Body model: SocialLoginData): Response<LoginModel>

    @GET("user/matchHistory")
    suspend fun getmatchHistory(@Header("Authorization") token: String): Response<MatchHistoryModel>

    @GET("user/question")
    suspend fun getQuestion(
        @Header("Authorization") token: String,
        @Query("category_id") id: Int
    ): Response<QuestionModel>

    @POST("user/reportQuestion")
    suspend fun submitreportQuestion(
        @Header("Authorization") token: String,
        @Body model: ReportQuestionDataModel
    ): Response<EmailSupportModel>

    @POST("user/question")
    suspend fun submitQuestion(
        @Header("Authorization") token: String,
        @Body model: QuestionSubmitDataModel
    ): Response<EmailSupportModel>

    @GET("masterBonus")
    suspend fun getBonus(@Header("Authorization") token: String): Response<DailyBonusModel>

}