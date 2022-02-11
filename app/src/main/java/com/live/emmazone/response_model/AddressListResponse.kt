package com.live.emmazone.response_model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AddressListResponse(
    @SerializedName("body")
    var body: List<Body>,
    @SerializedName("code")
    var code: Int, // 200
    @SerializedName("message")
    var message: String, // Address List
    @SerializedName("success")
    var success: Boolean // true
) : Serializable {
    data class Body(
        @SerializedName("address")
        var address: String, // Phase8
        @SerializedName("city")
        var city: String, // Mohali
        @SerializedName("createdAt")
        var createdAt: String, // 2022-02-02T10:10:12.000Z
        @SerializedName("id")
        var id: Int, // 19
        @SerializedName("latitude")
        var latitude: Int, // 31
        @SerializedName("longitude")
        var longitude: Int, // 77
        @SerializedName("name")
        var name: String, // siri
        @SerializedName("state")
        var state: String, // Punjab
        @SerializedName("updatedAt")
        var updatedAt: String, // 2022-02-02T10:10:12.000Z
        @SerializedName("userId")
        var userId: Int, // 183
        @SerializedName("zipcode")
        var zipcode: String, // 143001
        @SerializedName("isSelected")
        var isSelected: Boolean = false
    ) : Serializable
}