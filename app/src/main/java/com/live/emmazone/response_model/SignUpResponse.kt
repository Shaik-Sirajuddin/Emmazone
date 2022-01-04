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
        var created: Int, // 1641292683
        @SerializedName("createdAt")
        var createdAt: String, // 2022-01-04T10:38:03.000Z
        @SerializedName("email")
        var email: String, // ttt@cqlsys.co.uk
        @SerializedName("facebookId")
        var facebookId: String,
        @SerializedName("forgotPasswordHash")
        var forgotPasswordHash: String,
        @SerializedName("gender")
        var gender: Int, // 0
        @SerializedName("googleId")
        var googleId: String,
        @SerializedName("id")
        var id: Int, // 185
        @SerializedName("image")
        var image: String, // 41fd327a-5f72-411e-bec4-9a464a1481e0.jpg
        @SerializedName("isAt")
        var isAt: Int, // 1641281334
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
        @SerializedName("otp")
        var otp: Int, // 1111
        @SerializedName("password")
        var password: String, // $2y$10$11m5S2xA/vWQI227A6QC3OiLH84RzsRPc7uE3.ebrfPvhPInOYKk6
        @SerializedName("phone")
        var phone: String, // 7894564812
        @SerializedName("role")
        var role: Int, // 1
        @SerializedName("size")
        var size: String, // 0
        @SerializedName("status")
        var status: Int, // 1
        @SerializedName("token")
        var token: String, // eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhIjp7ImlkIjoxODUsImVtYWlsIjoidHR0QGNxbHN5cy5jby51ayJ9LCJpYXQiOjE2NDEyOTI2ODN9.dg5x-4Tk7ENotZb1O_QQgcY0Q_OGIpV_2uL5oWpN_7Y
        @SerializedName("updated")
        var updated: Int, // 1641292683
        @SerializedName("updatedAt")
        var updatedAt: String, // 2022-01-04T10:38:03.000Z
        @SerializedName("username")
        var username: String, // gagan
        @SerializedName("verified")
        var verified: Int // 0
    )
}