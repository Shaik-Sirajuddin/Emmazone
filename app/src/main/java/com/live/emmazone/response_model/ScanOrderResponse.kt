package com.live.emmazone.response_model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ScanOrderResponse (
    @SerializedName("code")
    var code: Int, // 200
    @SerializedName("message")
    var message: String, // Card added successfully.
    @SerializedName("success")
    var success: Boolean // true
 ):Serializable {
}