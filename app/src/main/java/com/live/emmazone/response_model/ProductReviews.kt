package com.live.emmazone.response_model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ProductReviews(
    @SerializedName("body")
    var body: Body,
    @SerializedName("code")
    var code: Int, // 200
    @SerializedName("message")
    var message: String, // User profile fetched successfully.
    @SerializedName("success")
    var success: Boolean // true
) : Serializable {
    data class Body(
        @SerializedName("productReview")
        var reviews : ArrayList<String>

    ) : Serializable
}