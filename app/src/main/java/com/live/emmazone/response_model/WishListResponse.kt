package com.live.emmazone.response_model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class WishListResponse(
    @SerializedName("body")
    var body: ArrayList<Body>,
    @SerializedName("code")
    var code: Int, // 200
    @SerializedName("message")
    var message: String, // Wish List
    @SerializedName("success")
    var success: Boolean // true
):Serializable {
    data class Body(
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
        var userId: Int // 263
    ):Serializable
}