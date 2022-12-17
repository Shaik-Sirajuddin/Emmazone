package com.live.emmazone.response_model

import com.google.gson.annotations.SerializedName
import com.live.emmazone.model.ModelShopStory
import java.io.Serializable

data class ShopStoryResponse(
    @SerializedName("body")
    var body: ArrayList<Shop>,
    @SerializedName("code")
    var code: Int, // 200
    @SerializedName("message")
    var message: String, // Signup successfully
    @SerializedName("success")
    var success: Boolean // true
):Serializable{
    data class Shop(
        @SerializedName("id")
        var id : String,
        @SerializedName("latitude")
        var latitude: String, // 30.71450000
        @SerializedName("longitude")
        var longitude: String, // 76.71490000
        @SerializedName("distance")
        var distance: Int, // 8738
        @SerializedName("isLiked")
        var isLiked: Int, // 1
        @SerializedName("rating")
        var ratings: String, // 0.0
        @SerializedName("shop_stories")
        var stories : ArrayList<ModelShopStory>
    ):Serializable
}