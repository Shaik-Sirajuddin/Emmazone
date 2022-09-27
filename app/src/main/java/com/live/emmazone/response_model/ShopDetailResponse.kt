package com.live.emmazone.response_model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ShopDetailResponse(
    @SerializedName("body")
    var body: Body,
    @SerializedName("code")
    var code: Int, // 200
    @SerializedName("message")
    var message: String, // Shop Details
    @SerializedName("success")
    var success: Boolean // true
) :Parcelable{
    @Parcelize
    data class Body(
        @SerializedName("cartCount")
        var cartCount: Int, // 0
        @SerializedName("distance")
        var distance: Int, // 8738
        @SerializedName("id")
        var id: Int, // 121
        @SerializedName("image")
        var image: String, // e4440279-0b7e-4de3-9bff]-68905a951de4.jpg
        @SerializedName("isLiked")
        var isLiked: Int, // 1
        @SerializedName("latitude")
        var latitude: String, // 30.71402850
        @SerializedName("longitude")
        var longitude: String, // 76.69094590
        @SerializedName("canRate")
        var canRate : Boolean,
        @SerializedName("notificationCount")
        var notificationCount: Int, // 0
        @SerializedName("products")
        var products: ArrayList<Product>,
        @SerializedName("ratings")
        var reviews : ArrayList<ShopReviewModel>,
        @SerializedName("rating")
        var ratings: String, // 0.0
        @SerializedName("shopAddress")
        var shopAddress: String, // D-199, 5th Floor Phase, 8-B, Industrial Area, Sector 74, Sahibzada Ajit Singh Nagar, Punjab 160071, India
        @SerializedName("shop_categories")
        var shopCategories: ArrayList<SellerShopDetailResponse.Body.ShopDetails.ShopCategory>,
        @SerializedName("shopDescription")
        var shopDescription: String, // All type garment available
        @SerializedName("shopName")
        var shopName: String, // Cqlsys
        @SerializedName("userId")
        var userId: Int, // 336
        @SerializedName("year")
        var year: Int // 2010
    ) :Parcelable{

    }
}