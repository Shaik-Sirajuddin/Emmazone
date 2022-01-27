package com.live.emmazone.response_model

data class ChangePasswordResponse(
    var body: Body,
    var code: Int, // 200
    var message: String, // Password  updated successfully.
    var success: Boolean // true
) {
    class Body(
    )
}