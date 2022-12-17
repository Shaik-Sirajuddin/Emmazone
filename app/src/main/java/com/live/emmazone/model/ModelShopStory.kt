package com.live.emmazone.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ModelShopStory(
    @SerializedName("id")
    val id : Int,
    @SerializedName("vendorId")
    val vendorId : Int ,
    @SerializedName("image")
    val image : String ,
    @SerializedName("expiryDate")
    val expiryDate : Long,
    @SerializedName("created")
    val created : Long
):Serializable