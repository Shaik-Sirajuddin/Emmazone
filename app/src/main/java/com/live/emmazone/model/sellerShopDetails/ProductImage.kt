package com.live.emmazone.model.sellerShopDetails

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductImage(
    val id: Int,
    val image: String,
    val isMainImage: Int,
    val product_id: Int
):Parcelable