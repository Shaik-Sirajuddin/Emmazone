package com.live.emmazone.model.sellerShopDetails

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SellerShopDetailsResponse(
    val body: Body,
    val code: Int,
    val message: String,
    val success: Boolean
):Parcelable