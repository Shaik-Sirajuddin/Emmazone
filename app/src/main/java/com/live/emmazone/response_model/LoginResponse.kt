package com.live.emmazone.response_model


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("body")
    var body: Body,
    @SerializedName("code")
    var code: Int, // 200
    @SerializedName("message")
    var message: String, // Login successfully
    @SerializedName("success")
    var success: Boolean // true
) {
    data class Body(
        @SerializedName("countryCode")
        var countryCode: String, // +91
        @SerializedName("created")
        var created: Int, // 1644225651
        @SerializedName("createdAt")
        var createdAt: String, // 2022-02-07T09:20:50.000Z
        @SerializedName("deviceToken")
        var deviceToken: String,
        @SerializedName("deviceType")
        var deviceType: Int, // 0
        @SerializedName("email")
        var email: String, // seller@gmail.com
        @SerializedName("facebookId")
        var facebookId: String,
        @SerializedName("forgotPasswordHash")
        var forgotPasswordHash: String,
        @SerializedName("gender")
        var gender: Int, // 0
        @SerializedName("googleId")
        var googleId: String,
        @SerializedName("id")
        var id: Int, // 263
        @SerializedName("image")
        var image: String, // fcfc67f8-b84e-4d61-91ec-1af1fc08b654.jpg
        @SerializedName("isAt")
        var isAt: Int, // 1644219269
        @SerializedName("isShopAdd")
        var isShopAdd: Int, // 1
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
        var password: String, // $2y$10$0nbPNijEayJj3v014Hvo0.7UffaTgEIpzkpYT9ioKObWTO8hEYMu.
        @SerializedName("phone")
        var phone: String, // 9988776654
        @SerializedName("role")
        var role: Int, // 3
        @SerializedName("status")
        var status: Int, // 1
        @SerializedName("token")
        var token: String, // eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhIjp7ImlkIjoyNjMsImVtYWlsIjoic2VsbGVyQGdtYWlsLmNvbSJ9LCJpYXQiOjE2NDQyMzU5NzZ9.fA0jBJv050WiC4dWaHEqJDoauYV2Jq1BcaZoGXVYHLI
        @SerializedName("total_bids")
        var totalBids: Int, // 1
        @SerializedName("updated")
        var updated: Int, // 1644225651
        @SerializedName("updatedAt")
        var updatedAt: String, // 2022-02-07T09:20:55.000Z
        @SerializedName("username")
        var username: String, // Seller
        @SerializedName("verified")
        var verified: Int // 1
    )
}