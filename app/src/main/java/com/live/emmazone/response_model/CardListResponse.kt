package com.live.emmazone.response_model


import com.google.gson.annotations.SerializedName

data class CardListResponse(
    @SerializedName("body")
    var body: ArrayList<Body>,
    @SerializedName("code")
    var code: Int, // 200
    @SerializedName("message")
    var message: String, // Card fetched successfully.
    @SerializedName("success")
    var success: Boolean // true
) {
    data class Body(
        @SerializedName("cardNumber")
        var cardNumber: String, // 4242424242425555
        @SerializedName("cardType")
        var cardType: Int, // 4
        @SerializedName("created")
        var created: Int, // 1645006849
        @SerializedName("createdAt")
        var createdAt: String, // 2022-02-16T10:20:49.000Z
        @SerializedName("id")
        var id: Int, // 139
        @SerializedName("isDefault")
        var isDefault: Int, // 0
        @SerializedName("month")
        var month: Int, // 10
        @SerializedName("name")
        var name: String, // user3
        @SerializedName("updated")
        var updated: Int, // 1645006849
        @SerializedName("updatedAt")
        var updatedAt: String, // 2022-02-16T10:20:49.000Z
        @SerializedName("userId")
        var userId: Int, // 267
        @SerializedName("year")
        var year: Int // 2024
    )
}