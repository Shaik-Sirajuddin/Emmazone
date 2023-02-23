package com.live.emmazone.response_model

import com.google.gson.annotations.SerializedName
import com.live.emmazone.model.DeletedShopsModel
import java.io.Serializable

data class DeletedShopsResponse(
    @SerializedName("body")
    var body: List<DeletedShopsModel>,
    @SerializedName("code")
    var code: Int, // 200
    @SerializedName("message")
    var message: String, //
    @SerializedName("success")
    var success: Boolean // true
) : Serializable