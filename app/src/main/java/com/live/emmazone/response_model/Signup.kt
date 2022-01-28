package com.live.emmazone.response_model


import com.google.gson.annotations.SerializedName

data class Signup(
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
        var created: Int, // 1643374282
        @SerializedName("createdAt")
        var createdAt: String, // 2022-01-28T12:51:22.000Z
        @SerializedName("email")
        var email: String, // sachin123@yopmail.com
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
        var image: String, // 4051d15a-953b-4e58-b550-faeb037564e6.png
        @SerializedName("isAt")
        var isAt: Int, // 1643364924
        @SerializedName("isVerified")
        var isVerified: Int, // 0
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
        var password: String, // $2y$10$8ewj.iSIS/yk7rAjqMZXr.5oEAm.tlLxWCqini0ZFDoRFkeFj7Hdu
        @SerializedName("phone")
        var phone: String, // 7807536707
        @SerializedName("role")
        var role: Int, // 3
        @SerializedName("status")
        var status: Int, // 1
        @SerializedName("token")
        var token: String, // eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhIjp7ImlkIjoyNjMsImVtYWlsIjoic2FjaGluMTIzQHlvcG1haWwuY29tIn0sImlhdCI6MTY0MzM3NDI4Mn0.WCTXLjHqwozx7Q6wgl-V-rOcqFUAfveGV1H_YQBIuAc
        @SerializedName("updated")
        var updated: Int, // 1643374282
        @SerializedName("updatedAt")
        var updatedAt: String, // 2022-01-28T12:51:22.000Z
        @SerializedName("username")
        var username: String, // sachin
        @SerializedName("verified")
        var verified: Int // 1
    )
}