package com.live.emmazone.response_model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ProductImage(
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("isMainImage")
    val isMainImage: Int,
    @SerializedName("product_id")
    val productId: Int
) : Serializable