package com.live.emmazone.response_model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data  class EditProductGroupResponse(
    @SerializedName("body")
    var body: AddProductGroupResponse.Body,
    @SerializedName("code")
    var code: Int, // 200
    @SerializedName("message")
    var message: String, // product added successfully
    @SerializedName("success")
    var success: Boolean // true
):Serializable