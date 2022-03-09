package com.live.emmazone.model

data class ShopProductDetailResponse(
    val body: Body,
    val code: Int,
    val message: String,
    val success: Boolean
) {
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
        val product_images: List<ProductImage>,
        val product_price: String,
        val product_quantity: Int,
        val product_size: ProductSize,
        val status: Int,
        val userId: Int
    ) {
        data class Category(
            val image: String,
            val name: String
        )

        data class ProductColor(
            val categoryColorId: Int,
            val color: String,
            val createdAt: String,
            val id: Int,
            val productId: Int,
            val updatedAt: String
        )

        data class ProductImage(
            val id: Int,
            val image: String,
            val isMainImage: Int,
            val product_id: Int
        )

        data class ProductSize(
            val categorySizeId: Int,
            val createdAt: String,
            val id: Int,
            val productId: Int,
            val size: String,
            val updatedAt: String
        )
    }
}