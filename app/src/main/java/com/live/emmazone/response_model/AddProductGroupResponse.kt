package com.live.emmazone.response_model

import com.google.gson.annotations.SerializedName
import com.live.emmazone.model.ProductVariant
import java.io.Serializable

data class AddProductGroupResponse(
    @SerializedName("body")
    var body: Body,
    @SerializedName("code")
    var code: Int, // 200
    @SerializedName("message")
    var message: String, // product added successfully
    @SerializedName("success")
    var success: Boolean // true
): Serializable {
    data class Body(
        @SerializedName("product_group")
        val group: ProductGroup
    ) : Serializable
}