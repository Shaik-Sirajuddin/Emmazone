package com.live.emmazone.response_model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class NotificationListingResponse(
    @SerializedName("body")
    var body: ArrayList<Body>,
    @SerializedName("code")
    var code: Int, // 200
    @SerializedName("message")
    var message: String, // Notification List
    @SerializedName("success")
    var success: Boolean // true
) : Serializable {
    data class Body(
        @SerializedName("created")
        var created: Int, // 1648038278
        @SerializedName("createdAt")
        var createdAt: String, // 2022-03-23T12:24:38.000Z
        @SerializedName("data")
        var `data`: String,
        @SerializedName("id")
        var id: Int, // 1
        @SerializedName("isRead")
        var isRead: Int, // 0
        @SerializedName("message")
        var message: String, // Your order is on the way
        @SerializedName("notificationType")
        var notificationType: Int, // 1
        @SerializedName("orderId")
        var orderId: Int, // 0
        @SerializedName("orderStatus")
        var orderStatus: Int, // 0
        @SerializedName("updated")
        var updated: Int, // 1648038278
        @SerializedName("updatedAt")
        var updatedAt: String, // 2022-03-23T12:24:38.000Z
        @SerializedName("user")
        var user: User,
        @SerializedName("user2Id")
        var user2Id: Int, // 335
        @SerializedName("userId")
        var userId: Int // 336
    ) : Serializable {
        data class User(
            @SerializedName("id")
            var id: Int, // 336
            @SerializedName("image")
            var image: String, // http://202.164.42.227:8188/uploads/user/e4440279-0b7e-4de3-9bff-68905a951de4.jpg
            @SerializedName("role")
            var role: Int, // 3
            @SerializedName("username")
            var username: String // Seller
        ) : Serializable
    }
}