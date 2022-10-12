package com.live.emmazone.response_model


import com.google.gson.annotations.SerializedName

data class RatingResponse(
    @SerializedName("body")
    var body: Body?,
    @SerializedName("code")
    var code: Int, // 200
    @SerializedName("message")
    var message: String, // Ratings Added successfully //Ratings fetched successfully
    @SerializedName("success")
    var success: Boolean // true
) {
    data class Body(
        @SerializedName("comment")
        var comment: String, // trending clothes here
        @SerializedName("createdAt")
        var createdAt: Int, // 1644400851
        @SerializedName("id")
        var id: Int, // 1
        @SerializedName("ratings")
        var ratings: String, // 4
        @SerializedName("status")
        var status: String, // 0
        @SerializedName("updatedAt")
        var updatedAt: Int, // 1644400851
        @SerializedName("userId")
        var userId: Int, // 267
        @SerializedName("vendorId")
        var vendorId: String // 23
    )
}