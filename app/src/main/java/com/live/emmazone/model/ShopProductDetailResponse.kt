package com.live.emmazone.model

import java.io.Serializable
import com.google.gson.annotations.SerializedName
import com.live.emmazone.response_model.Product
import com.live.emmazone.response_model.ShopDeliveryResponse


data class ShopProductDetailResponse(
    @SerializedName("body")
    var body: Body,
    @SerializedName("code")
    var code: Int, // 200
    @SerializedName("message")
    var message: String, // product Updated successfully
    @SerializedName("success")
    var success: Boolean // true
) : Serializable {
    data class Body(
        @SerializedName("cartCount")
        var cartCount: Int, // 1
        @SerializedName("notificationCount")
        var notificationCount: Int, // 2
        @SerializedName("taxValue")
        var taxValue: TaxValue?,
        @SerializedName("latitude")
        var latitude: String,
        @SerializedName("longitude")
        var longitude: String,
        @SerializedName("products")
        var products: ArrayList<Product>,
        @SerializedName("product_delivery")
        var productDelivery: ProductDeliveryModel,
        @SerializedName("shop_delivery")
        var shopDelivery: ShopDeliveryModel
    ) : Serializable {
        data class TaxValue(
            @SerializedName("comment")
            var comment: String,
            @SerializedName("created")
            var created: Int, // 1592274435
            @SerializedName("createdAt")
            var createdAt: String, // 2020-06-16T02:27:15.000Z
            @SerializedName("id")
            var id: Int, // 18
            @SerializedName("name")
            var name: String, // tax_value
            @SerializedName("updated")
            var updated: Int, // 1592274435
            @SerializedName("updatedAt")
            var updatedAt: String, // 2022-03-02T07:03:20.000Z
            @SerializedName("value")
            var value: String // 5
        ) : Serializable
    }
}