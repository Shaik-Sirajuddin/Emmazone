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
        var created: Int, // 1641294443
        @SerializedName("createdAt")
        var createdAt: String, // 2022-01-04T11:07:22.000Z
        @SerializedName("email")
        var email: String, // siri@yopmail.com
        @SerializedName("facebookId")
        var facebookId: String,
        @SerializedName("forgotPasswordHash")
        var forgotPasswordHash: String,
        @SerializedName("gender")
        var gender: Int, // 0
        @SerializedName("googleId")
        var googleId: String,
        @SerializedName("id")
        var id: Int, // 189
        @SerializedName("image")
        var image: String,
        @SerializedName("isAt")
        var isAt: Int, // 1641281334
        @SerializedName("isVerified")
        var is_verified: Int, // 0
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
        var password: String, // $2y$10$MNinHDlfKXeavP7ASE88yOgEQh.oO9GVZJguFkXnoxju9qIee0HT.
        @SerializedName("phone")
        var phone: String, // 8219879419
        @SerializedName("role")
        var role: Int, // 1
        @SerializedName("size")
        var size: String, // 0
        @SerializedName("status")
        var status: Int, // 1
        @SerializedName("token")
        var token: String, // eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhIjp7ImlkIjoxODksImVtYWlsIjoic2lyaUB5b3BtYWlsLmNvbSJ9LCJpYXQiOjE2NDEzNjY4NTl9.Ajt6Bo97yWqIh4O09mdE3L5KaaRc97YjpEvoIv7zX_g
        @SerializedName("updated")
        var updated: Int, // 1641294443
        @SerializedName("updatedAt")
        var updatedAt: String, // 2022-01-04T11:07:22.000Z
        @SerializedName("username")
        var username: String, // siri
        @SerializedName("verified")
        var verified: Int // 0
    )
} 