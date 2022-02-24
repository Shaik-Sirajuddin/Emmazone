package com.live.emmazone.model.sellerShopDetails

data class Body(
    val id: Int,
    val year: Int,
    val image: String,
    val latitude: String,
    val longitude: String,
    val products: List<Product>,
    val shop_categories: List<Category>,
    val shopAddress: String,
    val shopDescription: String,
    val shopName: String,
    val userId: Int
)