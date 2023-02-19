package com.live.emmazone.response_model

import com.google.gson.annotations.SerializedName
import com.live.emmazone.model.ShopDeliveryModel
import java.io.Serializable

class ShopDeliveryResponse(
    @SerializedName("body")
    val body: ShopDeliveryModel,
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
) : Serializable