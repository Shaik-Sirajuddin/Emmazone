package com.live.emmazone.response_model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class ShopDetailResponse(
    val body: Body,
    val code: Int,
    val message: String,
    val success: Boolean
) : Parcelable {
    @Parcelize
    data class Body(
        val id: Int,
        val image: String,
        val latitude: String,
        val longitude: String,
        val products: ArrayList<Product>,
        val shopAddress: String,
        val shopDescription: String,
        val shopName: String,
        val shop_categories: List<ShopCategory>,
        val userId: Int,
        val year: Int,
        var distance: Int,
        var ratings: String,
        var isLiked: Int
    ) : Parcelable {
        @Parcelize
        data class Product(
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
            val productReview: String,
            val product_highlight: Int,
            val product_images: List<ProductImage>,
            val product_price: String,
            val product_quantity: Int,
            val status: Int,
            val userId: Int
        ) : Parcelable {
            @Parcelize
            data class Category(
                val image: String,
                val name: String
            ) : Parcelable

            @Parcelize
            data class ProductImage(
                val id: Int,
                val image: String,
                val isMainImage: Int,
                val product_id: Int
            ) : Parcelable

            @Parcelize
            data class ProductColor(
                val categoryColorId: Int,
                val color: String,
                val createdAt: String,
                val id: Int,
                val productId: Int,
                val updatedAt: String
            ) : Parcelable

        }

        @Parcelize
        data class ShopCategory(
            val categoryId: Int,
            val categoryImage: String,
            val categoryName: String,
            val createdAt: String,
            val id: Int,
            val shopId: Int,
            val updatedAt: String,
            val vendorDetailId: Int
        ) : Parcelable
    }
}