package com.live.emmazone.response_model


import com.google.gson.annotations.SerializedName

data class CategoryListResponse(
    @SerializedName("body")
    var body: ArrayList<Body>,
    @SerializedName("code")
    var code: Int, // 200
    @SerializedName("message")
    var message: String, // Category List
    @SerializedName("success")
    var success: Boolean // true
) {
    data class Body(
        @SerializedName("id")
        var id: Int, // 6
        @SerializedName("image")
        var image: String, // http://202.164.42.227:8188/uploads/category/dc5c2de5-62b7-4825-8b43-b72e0cd28385.jpg
        @SerializedName("name")
        var name: String, // bagf
        var isSelected: Boolean = false
    )
}