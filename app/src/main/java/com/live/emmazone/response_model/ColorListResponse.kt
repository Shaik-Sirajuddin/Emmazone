package com.live.emmazone.response_model


import com.google.gson.annotations.SerializedName

data class ColorListResponse(
    @SerializedName("body")
    var body: ArrayList<Body>,
    @SerializedName("code")
    var code: Int, // 200
    @SerializedName("message")
    var message: String, // Color List
    @SerializedName("success")
    var success: Boolean // true
) {
    data class Body(
        @SerializedName("color")
        var color: String, // Red
        @SerializedName("createdAt")
        var createdAt: String, // 2022-01-31T05:52:00.000Z
        @SerializedName("id")
        var id: Int, // 1
        @SerializedName("updatedAt")
        var updatedAt: String, // 2022-01-31T05:52:00.000Z
        var isSelected: Boolean = false
    )
}