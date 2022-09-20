package com.live.emmazone.response_model

import com.google.gson.annotations.SerializedName
import com.live.emmazone.model.ProductVariant
import java.io.Serializable


data class SellerShopDetailResponse(
    @SerializedName("body")
    val body: Body,
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
) : Serializable {
    data class Body(
        @SerializedName("notificationCount")
        val notificationCount: Int,
        @SerializedName("ShopDetails")
        val shopDetails: ShopDetails,
        @SerializedName("groups")
        val groups : ArrayList<ProductGroup>
    ) : Serializable {
        data class ShopDetails(
            @SerializedName("id")
            val id: Int,
            @SerializedName("image")
            val image: String,
            @SerializedName("latitude")
            val latitude: String,
            @SerializedName("likesCount")
            val likesCount: Int,
            @SerializedName("longitude")
            val longitude: String,
            @SerializedName("notificationCount")
            val notificationCount: Int,
            @SerializedName("cartCount")
            val cartCount: Int?,
            @SerializedName("shopAddress")
            val shopAddress: String,
            @SerializedName("shop_categories")
            val shopCategories: ArrayList<ShopCategory>,
            @SerializedName("shopDescription")
            val shopDescription: String,
            @SerializedName("shopName")
            val shopName: String,
            @SerializedName("userId")
            val userId: Int,
            @SerializedName("year")
            val year: Int,
            @SerializedName("isLiked")
            val isLiked: Int,
            @SerializedName("ratings")
            val ratings: String,
            @SerializedName("distance")
            val distance: Int
        ) : Serializable {

            data class ShopCategory(
                @SerializedName("categoryId")
                val categoryId: Int,
                @SerializedName("categoryImage")
                val categoryImage: String,
                @SerializedName("categoryName")
                val categoryName: String,
                @SerializedName("createdAt")
                val createdAt: String,
                @SerializedName("id")
                val id: Int,
                @SerializedName("shopId")
                val shopId: Int,
                @SerializedName("updatedAt")
                val updatedAt: String,
                @SerializedName("vendorDetailId")
                val vendorDetailId: Int
            ) : Serializable
        }
    }
}