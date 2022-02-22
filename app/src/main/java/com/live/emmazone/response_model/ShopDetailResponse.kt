package com.live.emmazone.response_model

data class ShopDetailResponse(
    val body: Body,
    val code: Int,
    val message: String,
    val success: Boolean
) {
    data class Body(
        val distance: Int,
        val id: Int,
        val image: String,
        val isLiked: Int,
        val latitude: String,
        val longitude: String,
        val products: ArrayList<Product>,
        val ratings: String,
        val shopAddress: String,
        val shopDescription: String,
        val shopName: String,
        val shop_categories: ArrayList<ShopCategory>,
        val userId: Int
    ) {
        data class Product(
            val barcodeImage: String,
            val brandName: String,
            val category: String,
            val categoryId: Int,
            val created: Int,
            val createdAt: String,
            val description: String,
            val id: Int,
            val image: String,
            val isApproved: Int,
            val name: String,
            val product_color: String,
            val product_highlight: Int,
            val product_images: List<ProductImage>,
            val product_price: String,
            val product_quantity: Int,
            val product_type: Int,
            val size: String,
            val status: Int,
            val taxCategoryId: Int,
            val updated: Int,
            val updatedAt: String,
            val userId: Int,
            val vendorId: Int
        ) {
            data class ProductImage(
                val createdAt: Int,
                val id: Int,
                val image: String,
                val product_id: Int,
                val status: Int,
                val updatedAt: Int
            )
        }

        data class ShopCategory(
            val categoryId: Int,
            val category_name: String,
            val image: String
        )
    }
}