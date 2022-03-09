package com.live.emmazone.model.sellerShopDetailNew

data class Body(
    val id: Int,
    val image: String,
    val latitude: String,
    val longitude: String,
    val products: List<Product>,
    val category: List<Category>,
    val shopAddress: String,
    val shopDescription: String,
    val shopName: String,
    val userId: Int
)