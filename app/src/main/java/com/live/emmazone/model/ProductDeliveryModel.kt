package com.live.emmazone.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ProductDeliveryModel(
    @SerializedName("id")
    var id : Int,
    @SerializedName("bicycle_available")
    var bicycle_available : Boolean,
    @SerializedName("shop_available")
    var shop_available : Boolean,
    @SerializedName("logistics_available")
    var logistics_available : Boolean,
    @SerializedName("bicycle_price")
    var bicycle_price : Int,
    @SerializedName("shop_price")
    var shop_price : Int,
    @SerializedName("logistics_price")
    var logistics_price : Int,
    @SerializedName("product_group_id")
    var product_group_id : Int,

):Serializable