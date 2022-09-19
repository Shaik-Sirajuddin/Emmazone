package com.live.emmazone.response_model

import com.google.gson.annotations.SerializedName
import com.live.emmazone.model.ProductVariant
import java.io.Serializable

data class Product(
    @SerializedName("category")
    val category: Category,
    @SerializedName("categoryId")
    val categoryId: Int,
    @SerializedName("created")
    val created: Int,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("mainImage")
    val mainImage: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("minPrice")
    val minPrice : String,
    @SerializedName("product_ highlight")
    val productHighlight: Int,
    @SerializedName("product_images")
    val productImages: List<ProductImage>,
    @SerializedName("shortDescription")
    val shortDescription: String,
    @SerializedName("productReview")
    val productReview: String,
    @SerializedName("status")
    val status: Int,
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("product_variants")
    val productVariants : ArrayList<ProductVariant>
) : Serializable {
    data class Category(
        @SerializedName("image")
        val image: String,
        @SerializedName("name")
        val name: String
    ) : Serializable

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
}
