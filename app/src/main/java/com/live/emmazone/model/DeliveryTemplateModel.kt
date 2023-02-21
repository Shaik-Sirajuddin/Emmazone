package com.live.emmazone.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DeliveryTemplateModel(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("shop_price")
    var shop_price: Int = 0,
    @SerializedName("bicycle_price")
    var bicycle_price: Int = 0,
    @SerializedName("logistics_price")
    var logistics_price: Int = 0,
    @SerializedName("vendorId")
    var vendorId: Int = 0,
    @SerializedName("templateNo")
    var templateNo: Int = 0,
    @SerializedName("templateName")
    var templateName: String,
) : Serializable