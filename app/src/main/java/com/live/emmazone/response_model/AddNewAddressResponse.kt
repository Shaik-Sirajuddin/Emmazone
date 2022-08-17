package com.live.emmazone.response_model


import com.google.gson.annotations.SerializedName

data class AddNewAddressResponse(
    @SerializedName("body")
    var body: Body,
    @SerializedName("code")
    var code: Int, // 200
    @SerializedName("message")
    var message: String, // Address Added successfully
    @SerializedName("success")
    var success: Boolean // true
) {
    data class Body(
        @SerializedName("address")
        var address: String, // Phase8
        @SerializedName("city")
        var city: String, // Mohali
        @SerializedName("createdAt")
        var createdAt: String, // 2022-02-01T09:49:41.000Z
        @SerializedName("id")
        var id: Int, // 18
        @SerializedName("latitude")
        var latitude: Int, // 31
        @SerializedName("longitude")
        var longitude: Int, // 77
        @SerializedName("name")
        var name: String, // siri
        @SerializedName("state")
        var state: String, // Punjab
        @SerializedName("updatedAt")
        var updatedAt: String, // 2022-02-01T09:49:41.000Z
        @SerializedName("userId")
        var userId: Int, // 183
        @SerializedName("zipcode")
        var zipcode: String // 143001
    )
}