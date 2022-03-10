package com.live.emmazone.model.sellerShopDetails

import android.os.Parcelable
import com.live.emmazone.response_model.ShopDetailResponse
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Body(
    val id: Int,
    val year: Int,
    val image: String,
    val latitude: String,
    val longitude: String,
    val products: List<ShopDetailResponse.Body.Product>,
    val shop_categories: List<Category>,
    val shopAddress: String,
    val shopDescription: String,
    val shopName: String,
    val userId: Int,
    val likesCount: String
):Parcelable