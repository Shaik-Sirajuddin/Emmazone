package com.live.emmazone.response_model.socket_response


import com.google.gson.annotations.SerializedName

class ChatListResponse : ArrayList<ChatListResponse.ChatListResponseItem>() {
    data class ChatListResponseItem(
        @SerializedName("count")
        var count: Int, // 13
        @SerializedName("created")
        var created: Int, // 2147483647
        @SerializedName("createdAt")
        var createdAt: String, // 2022-04-04T09:09:26.000Z
        @SerializedName("deletedId")
        var deletedId: Int, // 0
        @SerializedName("id")
        var id: Int, // 2
        @SerializedName("image")
        var image: String, // 3f1df7e6-1eb5-4bab-a139-d0af0eaec891.jpg
        @SerializedName("isOnline")
        var isOnline: Int, // 0
        @SerializedName("lastMessage")
        var lastMessage: String, // ssffff
        @SerializedName("lastMessageId")
        var lastMessageId: Int, // 54
        @SerializedName("messageType")
        var messageType: Int, // 0
        @SerializedName("otherUserId")
        var otherUserId: Int, // 335
        @SerializedName("receiverId")
        var receiverId: Int, // 336
        @SerializedName("senderId")
        var senderId: Int, // 335
        @SerializedName("updated")
        var updated: Int, // 2147483647
        @SerializedName("updatedAt")
        var updatedAt: String, // 2022-04-06T05:29:11.000Z
        @SerializedName("userName")
        var userName: String, // User
        @SerializedName("shopName")
        var shopName: String, // Cqlsys
        @SerializedName("shopImage")
        var shopImage: String // Cqlsys
    )
}