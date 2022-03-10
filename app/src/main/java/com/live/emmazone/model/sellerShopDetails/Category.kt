package com.live.emmazone.model.sellerShopDetails

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Category(
    val categoryImage: String?,
    val categoryName: String?,
    val categoryId: Int?
):Parcelable