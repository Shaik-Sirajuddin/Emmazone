package com.live.emmazone.response_model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ShopDetailResponse(
    val body: SellerShopDetailResponse.Body,
    val code: Int,
    val message: String,
    val success: Boolean
):Parcelable
