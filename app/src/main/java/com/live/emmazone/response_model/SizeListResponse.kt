package com.live.emmazone.response_model


import com.google.gson.annotations.SerializedName

data class SizeListResponse(
    @SerializedName("body")
    var body: List<Body>,
    @SerializedName("code")
    var code: Int, // 200
    @SerializedName("message")
    var message: String, // Size List
    @SerializedName("success")
    var success: Boolean // true
) {
    data class Body(
        @SerializedName("createdAt")
        var createdAt: Int, // 1591617803
        @SerializedName("id")
        var id: Int, // 25
        @SerializedName("size")
        var size: Int, // 15
        @SerializedName("status")
        var status: Int, // 1
        @SerializedName("type")
        var type: Int, // 1
        @SerializedName("updatedAt")
        var updatedAt: Int // 1591617803
    )
}