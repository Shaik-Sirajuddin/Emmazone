package com.live.emmazone.response_model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PostalCodesResponse(
    @SerializedName("body")
    var body: ArrayList<PostalCodeItem>,
    @SerializedName("code")
    var code: Int, // 200
    @SerializedName("message")
    var message: String, // Privacy Policy
    @SerializedName("success")
    var success: Boolean
) : Serializable{
    data class PostalCodeItem(
        @SerializedName("id")
        var id : Int ,
        @SerializedName("postal_code")
        var postalCode : Int ,
    )
}