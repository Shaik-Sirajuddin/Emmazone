package com.live.emmazone.response_model
import com.google.gson.annotations.SerializedName


data class DeleteBankResponse(
    @SerializedName("body")
    var body: Int, // 1
    @SerializedName("code")
    var code: Int, // 200
    @SerializedName("message")
    var message: String, // Bank details deleted successfully
    @SerializedName("success")
    var success: Boolean // true
)