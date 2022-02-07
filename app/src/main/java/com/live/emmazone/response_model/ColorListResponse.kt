package com.live.emmazone.response_model


import com.google.gson.annotations.SerializedName

data class ColorListResponse(
    @SerializedName("body")
    var body: List<Body>,
    @SerializedName("code")
    var code: Int, // 200
    @SerializedName("message")
    var message: String, // Color List
    @SerializedName("success")
    var success: Boolean // true
) {
    data class Body(
        @SerializedName("color")
        var color: String, // Black
        @SerializedName("createdAt")
        var createdAt: String, // 2022-01-31T05:54:23.000Z
        @SerializedName("id")
        var id: Int, // 8
        @SerializedName("updatedAt")
        var updatedAt: String // 2022-01-31T05:54:23.000Z
    )
}