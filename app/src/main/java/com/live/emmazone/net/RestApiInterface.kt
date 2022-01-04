package com.live.emmazone.net


import com.live.emmazone.response_model.OtpResendResponse
import com.live.emmazone.response_model.OtpVerifyResponse
import com.live.emmazone.response_model.SignUpResponse
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
}