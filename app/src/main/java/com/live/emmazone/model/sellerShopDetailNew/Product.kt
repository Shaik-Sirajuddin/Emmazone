package com.live.emmazone.model.sellerShopDetailNew

data class Product(
    val categoryColorId: Int,
    val categoryId: Int,
    val categorySizeId: Int,
    val description: String,
    val id: Int,
    val mainImage: Any,
    val productReview: String,
    val product_highlight: Int,
    val product_images: List<ProductImage>,
    val product_price: String,
    val product_quantity: Int,
    val status: Int,
    val userId: Int
)