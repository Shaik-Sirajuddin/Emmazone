package com.live.emmazone.response_model


import com.google.gson.annotations.SerializedName

data class ForgotPasswordResponse(
    @SerializedName("body")
    var body: Body,
    @SerializedName("code")
    var code: Int, // 200
    @SerializedName("message")
    var message: String, // Forgot Password email sent successfully.
    @SerializedName("success")
    var success: Boolean // true
) {
    class Body
}