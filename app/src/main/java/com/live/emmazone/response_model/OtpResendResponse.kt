package com.live.emmazone.response_model

data class OtpResendResponse(
    var body: Body,
    var code: Int, // 200
    var message: String, // OTP resend successfully
    var success: Boolean // true
) {
    class Body
}