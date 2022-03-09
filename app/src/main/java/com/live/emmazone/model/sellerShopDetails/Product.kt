package com.live.emmazone.model.sellerShopDetails

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    val category: Category,
    val categoryColorId: Int,
    val categoryId: Int,
    val categorySizeId: Int,
    val description: String,
    val id: Int,
    val name: String,
    val mainImage: String,
    val productReview: String,
    val product_highlight: Int,
    val product_images: List<ProductImage>,
    val product_price: String,
    val product_quantity: Int,
    val status: Int,
    val userId: Int
):Parcelable