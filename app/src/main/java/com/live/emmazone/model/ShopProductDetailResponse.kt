package com.live.emmazone.model

import java.io.Serializable

data class ShopProductDetailResponse(
    val body: Body,
    val code: Int,
    val message: String,
    val success: Boolean
):Serializable {
    data class Body(
        val category: Category,
        val categoryColorId: Int,
        val categoryId: Int,
        val categorySizeId: Int,
        val created: Int,
        val createdAt: String,
        val description: String,
        val id: Int,
        val mainImage: String,
        val name: String,
        val productColor: ProductColor,
        val productReview: String,
        val product_highlight: Int,
        val product_images: ArrayList<ProductImage>,
        val product_price: String,
        val product_quantity: Int,
        val notificationCount: Int,
        val cartCount: Int,
        val product_size: ProductSize,
        val status: Int,
        val shortDescription: String,
        val userId: Int
    ) :Serializable{
        data class Category(
            val image: String,
            val name: String
        ):Serializable

        data class ProductColor(
            val categoryColorId: Int,
            val color: String,
            val createdAt: String,
            val id: Int,
            val productId: Int,
            val updatedAt: String
        ):Serializable

        data class ProductImage(
            val id: Int,
            val image: String,
            val isMainImage: Int,
            val product_id: Int
        ):Serializable

        data class ProductSize(
            val categorySizeId: Int,
            val createdAt: String,
            val id: Int,
            val productId: Int,
            val size: String,
            val updatedAt: String
        ):Serializable
    }
}