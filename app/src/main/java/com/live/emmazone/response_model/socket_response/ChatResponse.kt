package com.live.emmazone.response_model.socket_response


import com.google.gson.annotations.SerializedName

class ChatResponse : ArrayList<ChatResponse.ChatResponseItem>(){
    data class ChatResponseItem(
        @SerializedName("chatConstantId")
        var chatConstantId: Int, // 2
        @SerializedName("created")
        var created: Int, // 2147483647
        @SerializedName("deletedId")
        var deletedId: Int, // 0
        @SerializedName("id")
        var id: Int, // 7
        @SerializedName("message")
        var message: String, // qqqqqq
        @SerializedName("messageType")
        var messageType: Int, // 0
        @SerializedName("readStatus")
        var readStatus: Int, // 0
        @SerializedName("receiverId")
        var receiverId: Int, // 335
        @SerializedName("receiverImage")
        var receiverImage: String, // http://202.164.42.227:8188/uploads/user/e4440279-0b7e-4de3-9bff-68905a951de4.jpg
        @SerializedName("receiverName")
        var receiverName: String, // Seller
        @SerializedName("senderId")
        var senderId: Int, // 336
        @SerializedName("senderImage")
        var senderImage: String, // http://202.164.42.227:8188/uploads/user/3f1df7e6-1eb5-4bab-a139-d0af0eaec891.jpg
        @SerializedName("senderName")
        var senderName: String, // User
        @SerializedName("updated")
        var updated: Int // 2147483647
    )
}