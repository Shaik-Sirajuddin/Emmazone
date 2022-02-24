package com.live.emmazone.response_model


import com.google.gson.annotations.SerializedName

data class CommonResponse(
    @SerializedName("code")
    var code: Int, // 200
    @SerializedName("message")
    var message: String, // Card added successfully.
    @SerializedName("success")
    var success: Boolean // true
)