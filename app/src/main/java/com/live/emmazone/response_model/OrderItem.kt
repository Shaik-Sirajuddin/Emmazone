package com.live.emmazone.response_model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class OrderItem(
    @SerializedName("id")
    var id : Int ,
    @SerializedName("registerCode")
    var registerCode : Long,
):Serializable