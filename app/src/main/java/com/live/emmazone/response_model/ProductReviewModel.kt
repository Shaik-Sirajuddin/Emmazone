package com.live.emmazone.response_model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ProductReviewModel
    (
    @SerializedName("id")
    val id: Int,
    @SerializedName("userId")
    val userId : Int,
    @SerializedName("productId")
    val productId : Int,
    @SerializedName("rating")
    val rating : Int,
    @SerializedName("comment")
    val comment : String,
    @SerializedName("updated")
    val updated : Long,
    @SerializedName("created")
    val created : Long,
    @SerializedName("user")
    val user : User
    ) : Serializable{
        data class User(
            @SerializedName("id")
            val id:Int,
            @SerializedName("image")
            val image : String,
            @SerializedName("name")
            val name : String,
        ):Serializable
    }