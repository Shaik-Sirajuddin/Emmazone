package com.live.emmazone.response_model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ProfileResponse(
    @SerializedName("body")
    var body: Body,
    @SerializedName("code")
    var code: Int, // 200
    @SerializedName("message")
    var message: String, // User profile fetched successfully.
    @SerializedName("success")
    var success: Boolean // true
):Serializable {
    data class Body(
        @SerializedName("countryCode")
        var countryCode: String, // +91
        @SerializedName("created")
        var created: Int, // 1641274768
        @SerializedName("createdAt")
        var createdAt: String, // 2022-01-04T05:39:27.000Z
        @SerializedName("email")
        var email: String, // hello2@cqlsys.co.uk
        @SerializedName("facebookId")
        var facebookId: String,
        @SerializedName("forgotPasswordHash")
        var forgotPasswordHash: String,
        @SerializedName("gender")
        var gender: Int, // 0
        @SerializedName("googleId")
        var googleId: String,
        @SerializedName("id")
        var id: Int, // 183
        @SerializedName("image")
        var image: String?, // 3881d5d5-425f-4b98-a87d-83ee255b1b15.jpg
        @SerializedName("isAt")
        var isAt: Int, // 1641274740
        @SerializedName("isVerified")
        var is_Verified: Int, // 0
        @SerializedName("latitude")
        var latitude: String, // 0.000000
        @SerializedName("location")
        var location: String,
        @SerializedName("longitude")
        var longitude: String, // 0.000000
        @SerializedName("nameuser")
        var nameuser: String,
        @SerializedName("notification_status")
        var notificationStatus: Int, // 0
        @SerializedName("otp")
        var otp: Int, // 1111
        @SerializedName("password")
        var password: String, // $2y$10$QLblWQIdPYh0j7kymdcJZutj3k953KKFM6GFFEmRgj/b0.T64aTcK
        @SerializedName("phone")
        var phone: String, // 7894564812
        @SerializedName("role")
        var role: Int, // 1
        @SerializedName("status")
        var status: Int, // 1
        @SerializedName("updated")
        var updated: Int, // 1641274768
        @SerializedName("updatedAt")
        var updatedAt: String, // 2022-01-13T05:42:38.000Z
        @SerializedName("username")
        var username: String, // hello2
        @SerializedName("verified")
        var verified: Int // 0
    ):Serializable
}