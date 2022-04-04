package com.live.emmazone.response_model.socket_response


import com.google.gson.annotations.SerializedName

data class NewMsgResopnse(
    @SerializedName("chatConstantId")
    var chatConstantId: Int, // 2
    @SerializedName("created")
    var created: Int, // 2147483647
    @SerializedName("deletedId")
    var deletedId: Int, // 0
    @SerializedName("forwadedMessageId")
    var forwadedMessageId: Int, // 0
    @SerializedName("groupId")
    var groupId: Int, // 0
    @SerializedName("id")
    var id: Int, // 7
    @SerializedName("isBroadcast")
    var isBroadcast: Int, // 0
    @SerializedName("lat")
    var lat: Int, // 0
    @SerializedName("long")
    var long: Int, // 0
    @SerializedName("message")
    var message: String, // qqqqqq
    @SerializedName("messageType")
    var messageType: Int, // 0
    @SerializedName("readStatus")
    var readStatus: Int, // 0
    @SerializedName("receiverId")
    var receiverId: Int, // 335
    @SerializedName("receiverImage")
    var receiverImage: String, // 3f1df7e6-1eb5-4bab-a139-d0af0eaec891.jpg
    @SerializedName("receiverName")
    var receiverName: String, // User
    @SerializedName("replyMessage")
    var replyMessage: String,
    @SerializedName("replyMessageId")
    var replyMessageId: Int, // 0
    @SerializedName("replyMessageOwnerId")
    var replyMessageOwnerId: Int, // 0
    @SerializedName("replyMessageType")
    var replyMessageType: Int, // 0
    @SerializedName("senderId")
    var senderId: Int, // 336
    @SerializedName("senderImage")
    var senderImage: String, // e4440279-0b7e-4de3-9bff-68905a951de4.jpg
    @SerializedName("senderName")
    var senderName: String, // Seller
    @SerializedName("thumbnail")
    var thumbnail: String,
    @SerializedName("updated")
    var updated: Int // 2147483647
)