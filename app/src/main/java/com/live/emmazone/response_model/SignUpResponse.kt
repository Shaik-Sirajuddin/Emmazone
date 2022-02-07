package com.live.emmazone.response_model


import com.google.gson.annotations.SerializedName

data class SignUpResponse(
    @SerializedName("body")
    var body: Body,
    @SerializedName("code")
    var code: Int, // 200
    @SerializedName("message")
    var message: String, // Signup successfully
    @SerializedName("success")
    var success: Boolean // true
) {
    data class Body(
        @SerializedName("countryCode")
        var countryCode: String, // +91
        @SerializedName("created")
        var created: Int, // 1644237158
        @SerializedName("createdAt")
        var createdAt: String, // 2022-02-07T12:32:37.000Z
        @SerializedName("deviceToken")
        var deviceToken: String,
        @SerializedName("deviceType")
        var deviceType: Int, // 0
        @SerializedName("email")
        var email: String, // sellerrrrrd@gmail.coml18
        @SerializedName("facebookId")
        var facebookId: String,
        @SerializedName("forgotPasswordHash")
        var forgotPasswordHash: String,
        @SerializedName("gender")
        var gender: Int, // 0
        @SerializedName("googleId")
        var googleId: String,
        @SerializedName("id")
        var id: Int, // 266
        @SerializedName("image")
        var image: String,
        @SerializedName("isAt")
        var isAt: Int, // 1644228111
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
        var notificationStatus: Int, // 1
        @SerializedName("otp")
        var otp: Int, // 1111
        @SerializedName("password")
        var password: String, // $2y$10$UaTOv14ve1sHNTYBtKqwt.NwfhJhr1NgP/jYdDRUpjmX81wlcRaMm
        @SerializedName("phone")
        var phone: String, // 99887201438
        @SerializedName("role")
        var role: Int, // 3
        @SerializedName("status")
        var status: Int, // 1
        @SerializedName("token")
        var token: String, // eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhIjp7ImlkIjoyNjYsImVtYWlsIjoic2VsbGVycnJycmRAZ21haWwuY29tbDE4In0sImlhdCI6MTY0NDIzNzE1OH0.Hs9Gichwo6sa_jzkub3ZGTreUwaQRkSvbDmrpyNN_Lc
        @SerializedName("total_bids")
        var totalBids: Int, // 1
        @SerializedName("updated")
        var updated: Int, // 1644237158
        @SerializedName("updatedAt")
        var updatedAt: String, // 2022-02-07T12:32:37.000Z
        @SerializedName("username")
        var username: String, // sellerrrr
        @SerializedName("verified")
        var verified: Int // 1
    )
}