package com.live.emmazone.response_model

import com.google.gson.annotations.SerializedName
import com.live.emmazone.model.ProductDeliveryModel
import java.io.Serializable

data class ProductGroup(
    @SerializedName("category")
    val category: Category,
    @SerializedName("categoryId")
    val categoryId: Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("mainImage")
    val mainImage: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("product_highlight")
    val productHighlight: Int,
    @SerializedName("product_images")
    val productImages: List<ProductImage>,
    @SerializedName("shortDescription")
    val shortDescription: String,
    @SerializedName("products")
    var products : ArrayList<Product> = arrayListOf(),
    @SerializedName("registerCode")
    var registerCode : Int,
    @SerializedName("product_delivery")
    var productDelivery : ProductDeliveryModel

) : Serializable {
    data class Category(
        @SerializedName("image")
        val image: String,
        @SerializedName("name")
        val name: String
    ) : Serializable


}