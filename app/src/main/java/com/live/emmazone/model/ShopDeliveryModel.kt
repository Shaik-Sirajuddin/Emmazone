package com.live.emmazone.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ShopDeliveryModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("vendorId")
    val vendorId: Int,
    @SerializedName("bicycle_available")
    val bicycle_available: Boolean,
    @SerializedName("shop_available")
    val shop_available: Boolean,
    @SerializedName("logistics_available")
    val logistics_available: Boolean,
    @SerializedName("limit_price")
    val limit_price: Int?,
    @SerializedName("serviceable")
    val serviceable: Boolean = false
) : Serializable