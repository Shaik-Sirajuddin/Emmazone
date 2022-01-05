package com.live.emmazone.response_model

data class OtpVerifyResponse(
    var body: Body,
    var code: Int, // 200
    var message: String, // OTP verified successfully.
    var success: Boolean // true
) {
    class Body
}