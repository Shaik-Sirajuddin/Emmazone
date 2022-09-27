package com.live.emmazone.response_model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ProductReviewResponse(
    @SerializedName("body")
    var review: ProductReviewModel?,
    @SerializedName("code")
    var code: Int, // 200
    @SerializedName("message")
    var message: String,
    @SerializedName("success")
    var success: Boolean
):Serializable