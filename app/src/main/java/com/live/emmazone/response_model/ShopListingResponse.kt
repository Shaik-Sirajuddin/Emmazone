package com.live.emmazone.response_model


import com.google.gson.annotations.SerializedName

data class ShopListingResponse(
    @SerializedName("body")
    var body: List<Body>,
    @SerializedName("code")
    var code: Int, // 200
    @SerializedName("message")
    var message: String, // Shop listing
    @SerializedName("success")
    var success: Boolean // true
) {
    data class Body(
        @SerializedName("distance")
        var distance: Int, // 1
        @SerializedName("id")
        var id: Int, // 36
        @SerializedName("isLiked")
        var isLiked: Int, // 0
        @SerializedName("latitude")
        var latitude: String, // 30.71450000
        @SerializedName("longitude")
        var longitude: String, // 76.71490000
        @SerializedName("ratings")
        var ratings: String, // 0.0
        @SerializedName("shopDescription")
        var shopDescription: String, // here
        @SerializedName("shopName")
        var shopName: String, // clothing HUB
        @SerializedName("userId")
        var userId: Int // 248
    )
}