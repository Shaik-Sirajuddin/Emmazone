package com.live.emmazone.response_model

import com.google.gson.annotations.SerializedName
import com.live.emmazone.model.ProductDeliveryModel
import com.live.emmazone.model.ShopDeliveryModel

data class ProductDeliveryResponse(
    @SerializedName("body")
    var body: Body,
    @SerializedName("code")
    var code: Int, // 200
    @SerializedName("message")
    var message: String, // Privacy Policy
    @SerializedName("success")
    var success: Boolean // true
) {
    data class Body(
        @SerializedName("product_delivery")
        var productDelivery: ProductDeliveryModel,
        @SerializedName("shop_delivery")
        var shopDelivery: ShopDeliveryModel
    )
}