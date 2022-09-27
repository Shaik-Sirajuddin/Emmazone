package com.live.emmazone.response_model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ShopReviewModel
    (
    @SerializedName("id")
    val id: Int,
    @SerializedName("userId")
    val userId : Int,
    @SerializedName("vendorId")
    val vendorId : Int,
    @SerializedName("ratings")
    val rating : Int,
    @SerializedName("comment")
    val comment : String,
    @SerializedName("updatedAt")
    val updated : Long,
    @SerializedName("createdAt")
    val created : Long,
    @SerializedName("user")
    val user : User
) : Serializable {
    data class User(
        @SerializedName("id")
        val id:Int,
        @SerializedName("image")
        val image : String,
        @SerializedName("name")
        val name : String,
    ): Serializable
}