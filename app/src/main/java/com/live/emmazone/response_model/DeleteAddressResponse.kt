package com.live.emmazone.response_model


import com.google.gson.annotations.SerializedName

data class DeleteAddressResponse(
    @SerializedName("body")
    var body: Int, // 0
    @SerializedName("code")
    var code: Int, // 200
    @SerializedName("message")
    var message: String, // Delete address successfully
    @SerializedName("success")
    var success: Boolean // true
)