package com.live.emmazone.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DeletedShopsModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("vendorId")
    val vendorId: Int,
    @SerializedName("shopName")
    val shopName: String,
    @SerializedName("created")
    var created: Long, // 1648552687
    @SerializedName("updated")
    var updated: Long, // 1648552687
) : Serializable