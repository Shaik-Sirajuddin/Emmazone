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
) : Serializable {
    data class Body(
        @SerializedName("cartCount")
        var cartCount: Int, // 1
        @SerializedName("notificationCount")
        var notificationCount: Int, // 0
        @SerializedName("User")
        var user: User
    ) : Serializable {
        data class User(
            @SerializedName("countryCode")
            var countryCode: String, // +91
            @SerializedName("created")
            var created: Int, // 1644382245
            @SerializedName("createdAt")
            var createdAt: String, // 2022-02-09T04:50:45.000Z
            @SerializedName("deviceToken")
            var deviceToken: String,
            @SerializedName("deviceType")
            var deviceType: Int, // 0
            @SerializedName("email")
            var email: String, // user@gmail.com
            @SerializedName("facebookId")
            var facebookId: String,
            @SerializedName("forgotPasswordHash")
            var forgotPasswordHash: String,
            @SerializedName("gender")
            var gender: Int, // 0
            @SerializedName("googleId")
            var googleId: String,
            @SerializedName("id")
            var id: Int, // 267
            @SerializedName("image")
            var image: String, // f4859e2f-e933-4a98-a122-c263f9e99b2a.jpg
            @SerializedName("isAt")
            var isAt: Int, // 1644326785
            @SerializedName("isVerified")
            var is_verified: Int, // 1
            @SerializedName("latitude")
            var latitude: String, // 0.000000
            @SerializedName("location")
            var location: String,
            @SerializedName("longitude")
            var longitude: String, // 0.000000
            @SerializedName("nameuser")
            var nameuser: String,
            @SerializedName("notification_status")
            var notificationStatus: Int, // 1
            @SerializedName("otp")
            var otp: Int, // 1111
            @SerializedName("password")
            var password: String, // $2y$10$3rSEIDApz.3zDNRZFJdT5era0zvHUjqG7spWfnJng9Ns51qBNWFbS
            @SerializedName("phone")
            var phone: String, // 114477885522
            @SerializedName("role")
            var role: Int, // 1
            @SerializedName("status")
            var status: Int, // 1
            @SerializedName("total_bids")
            var totalBids: Int, // 1
            @SerializedName("updated")
            var updated: Int, // 1644382245
            @SerializedName("updatedAt")
            var updatedAt: String, // 2022-03-10T05:00:37.000Z
            @SerializedName("username")
            var username: String, // test user
            @SerializedName("verified")
            var verified: Int, // 1
            @SerializedName("walletBalance")
            var walletBalance: String // 0.00
        ) : Serializable
    }
}