package com.live.emmazone.response_model


import com.google.gson.annotations.SerializedName
import com.live.emmazone.model.ModelShopStory
import java.io.Serializable

data class WishListResponse(
    @SerializedName("body")
    var body: Body,
    @SerializedName("code")
    var code: Int, // 200
    @SerializedName("message")
    var message: String, // Wish List
    @SerializedName("success")
    var success: Boolean // true
) : Serializable {
    data class Body(
        @SerializedName("cartCount")
        var cartCount: Int, // 1
        @SerializedName("notificationCount")
        var notificationCount: Int, // 0
        @SerializedName("wishList")
        var wishList: ArrayList<Wish>
    ) : Serializable {
        data class Wish(
            @SerializedName("distance")
            var distance: Int, // 5431
            @SerializedName("id")
            var id: Int, // 65
            @SerializedName("image")
            var image: String, // fcfc67f8-b84e-4d61-91ec-1af1fc08b654.jpg
            @SerializedName("isLiked")
            var isLiked: Int, // 1
            @SerializedName("latitude")
            var latitude: String, // 30.71450000
            @SerializedName("longitude")
            var longitude: String, // 76.71490000
            @SerializedName("ratings")
            var ratings: String, // 5.0
            @SerializedName("shopDescription")
            var shopDescription: String, // visit
            @SerializedName("shopName")
            var shopName: String, // collection
            @SerializedName("userId")
            var userId: Int, // 263
            @SerializedName("shop_stories")
            var stories: ArrayList<ModelShopStory>
        ) : Serializable
    }
}