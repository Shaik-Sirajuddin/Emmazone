package com.live.emmazone.model

import com.live.emmazone.model.sellerShopDetailNew.ProductImage
import com.live.emmazone.response_model.ShopDetailResponse

data class CartResponsModel(
    val body: Body,
    val code: Int,
    val message: String,
    val success: Boolean
) {
    data class Body(
        val cartItems: ArrayList<CartItem>,
        val deliveryCharge: Int,
        val subTotal: Int,
        val tax: Int,
        val taxType: Int,
        val total: Double,
        val walletBalance: String,
        val youMayLikeProducts: ArrayList<YouMayLikeProducts>
    ) {
        data class CartItem(
            val created: Int,
            val createdAt: String,
            val id: Int,
            val product: Product,
            val productId: Int,
            var qty: Int,
            val totalQtyPriceForThisProduct: Int,
            val updated: Int,
            val updatedAt: String,
            val userId: Int
        ) {
            data class Product(
                val categoryColorId: Int,
                val categoryId: Int,
                val categorySizeId: Int,
                val created: Int,
                val createdAt: String,
                val description: String,
                val id: Int,
                val mainImage: String,
                val name: String,
                val productReview: String,
                val product_highlight: Int,
                val product_price: String,
                val product_quantity: Int,
                val status: Int,
                val userId: Int,
                val product_images: List<ProductImage>?=null
            )
        }

        data class YouMayLikeProducts(
            val categoryColorId: Int,
            val categoryId: Int,
            val categorySizeId: Int,
            val created: Int,
            val createdAt: String,
            val description: String,
            val id: Int,
            val mainImage: String,
            val name: String,
            val productReview: String,
            val product_highlight: Int,
            val product_price: String,
            val product_quantity: Int,
            val status: Int,
            val userId: Int,
            val product_images: List<ProductImage>?
        )
    }
}