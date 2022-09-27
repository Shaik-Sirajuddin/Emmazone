package com.live.emmazone.response_model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ReviewsResponse(
    @SerializedName("body")
    var body: ArrayList<ProductReviewModel>,
    @SerializedName("code")
    var code: Int, // 200
    @SerializedName("message")
    var message: String, // Ratings Added successfully
    @SerializedName("success")
    var success: Boolean // true
) {
//    data class Body(
//        @SerializedName("productReview")
//        val reviews :
//    ):Serializable
}