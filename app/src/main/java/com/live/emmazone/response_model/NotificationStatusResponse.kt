package com.live.emmazone.response_model


import com.google.gson.annotations.SerializedName

data class NotificationStatusResponse(
    @SerializedName("body")
    var body: Body,
    @SerializedName("code")
    var code: Int, // 200
    @SerializedName("message")
    var message: String, // Status updated successfully.
    @SerializedName("success")
    var success: Boolean // true
) {
    data class Body(
        @SerializedName("countryCode")
        var countryCode: String, // +1
        @SerializedName("created")
        var created: Int, // 1643280336
        @SerializedName("createdAt")
        var createdAt: String, // 2022-01-27T10:45:35.000Z
        @SerializedName("email")
        var email: String, // test@yopmail.com
        @SerializedName("facebookId")
        var facebookId: String,
        @SerializedName("forgotPasswordHash")
        var forgotPasswordHash: String,
        @SerializedName("gender")
        var gender: Int, // 0
        @SerializedName("googleId")
        var googleId: String,
        @SerializedName("id")
        var id: Int, // 248
        @SerializedName("image")
        var image: String, // 3dd6bdb1-ea58-4ae9-8aad-110bbb08359e.jpg
        @SerializedName("isAt")
        var isAt: Int, // 1643279823
        @SerializedName("isVerified")
        var is_Verified: Int, // 1
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
        var password: String, // $2y$10$N2ErATvKg.2g/YQ1P76JYuZ9p8sAxqyYGjfovr2O0/hr/KYyrpX1G
        @SerializedName("phone")
        var phone: String, // 6589774
        @SerializedName("role")
        var role: Int, // 3
        @SerializedName("status")
        var status: Int, // 1
        @SerializedName("updated")
        var updated: Int, // 1643280336
        @SerializedName("updatedAt")
        var updatedAt: String, // 2022-01-31T05:55:14.000Z
        @SerializedName("username")
        var username: String, // test
        @SerializedName("verified")
        var verified: Int // 1
    )
}