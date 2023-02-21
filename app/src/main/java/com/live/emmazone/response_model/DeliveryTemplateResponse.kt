package com.live.emmazone.response_model

import com.google.gson.annotations.SerializedName
import com.live.emmazone.model.DeliveryTemplateModel
import java.io.Serializable

data class DeliveryTemplateResponse(
    @SerializedName("body")
    var body: List<DeliveryTemplateModel>,
    @SerializedName("code")
    var code: Int, // 200
    @SerializedName("message")
    var message: String, //
    @SerializedName("success")
    var success: Boolean // true
) : Serializable