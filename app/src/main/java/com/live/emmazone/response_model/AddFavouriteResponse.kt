package com.live.emmazone.response_model


import com.google.gson.annotations.SerializedName

data class AddFavouriteResponse(
    @SerializedName("body")
    var body: Body,
    @SerializedName("code")
    var code: Int, // 200
    @SerializedName("message")
    var message: String, // Fav status changed successfully.
    @SerializedName("success")
    var success: Boolean // true
) {
    data class Body(
        @SerializedName("status")
        var status: Int // 0
    )
}