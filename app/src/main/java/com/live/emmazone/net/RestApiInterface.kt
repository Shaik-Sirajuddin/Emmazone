package com.live.emmazone.net


import com.live.emmazone.response_model.*
import com.live.emmazone.utils.AppConstants
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface RestApiInterface {

    @Multipart
    @POST(AppConstants.SIGN_UP)
    fun signUp(
        @PartMap hashMap: HashMap<String, RequestBody>,
        @Part image: MultipartBody.Part
    ): Call<SignUpResponse>


    @FormUrlEncoded
    @POST(AppConstants.OTP_VERIFY)
    fun otpVerify(
        @FieldMap hashMap: HashMap<String, String>
    ): Call<OtpVerifyResponse>


    @POST(AppConstants.RESEND_OTP)
    fun resendOtp(
    ): Call<OtpResendResponse>


    @FormUrlEncoded
    @POST(AppConstants.LOGIN)
    fun login(
        @FieldMap hashMap: HashMap<String, String>
    ): Call<LoginResponse>

    @GET(AppConstants.TERMS)
    fun termsCondition(): Call<TermsConditionResponse>

    @GET(AppConstants.PRIVACY_POLICY)
    fun privacyPolicy(): Call<PrivacyPolicyResponse>

    @FormUrlEncoded
    @POST(AppConstants.FORGOT_PASSWORD)
    fun forgotPassword(@FieldMap hashMap: HashMap<String, String>): Call<ForgotPasswordResponse>


    @GET(AppConstants.CATEGORY_LIST)
    fun categoryList(): Call<CategoryListResponse>

    @Multipart
    @POST(AppConstants.ADD_SHOP)
    fun addShop(@PartMap hashMap: HashMap<String, RequestBody>,
    @Part image: MultipartBody.Part): Call<AddShopResponse>

}