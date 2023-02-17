package com.live.emmazone.response_model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ShopDeliveryResponse(
    @SerializedName("body")
    val body: Body,
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
) : Serializable {
    class Body(
        @SerializedName("id")
        val id : Int ,
        @SerializedName("vendorId")
        val vendorId : Int ,
        @SerializedName("bicycle_available")
        val bicycle_available : Boolean,
        @SerializedName("shop_available")
        val shop_available : Boolean,
        @SerializedName("logistics_available")
        val logistics_available: Boolean,
        @SerializedName("limit_price")
        val limit_price : Int?,
    ):Serializable
}