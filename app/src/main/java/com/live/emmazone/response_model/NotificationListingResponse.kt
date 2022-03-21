package com.live.emmazone.response_model

data class NotificationListingResponse(
    var body: ArrayList<Body>,
    var code: Int, // 200
    var message: String, // Notification List
    var success: Boolean // true
) {
    data class Body(
        var created: Int, // 1636634573
        var createdAt: String, // 2021-11-11T12:42:52.000Z
        var `data`: String,
        var id: Int, // 624
        var isRead: Int, // 0
        var message: String, // fy7uohui
        var notificationType: Int, // 4
        var updated: Int, // 1636634573
        var updatedAt: String, // 2021-11-11T12:42:52.000Z
        var user2Id: Int, // 251
        var userId: Int, // 0
        var user_image: String,
        var username: String
    )
}