package com.live.emmazone.response_model

import com.google.gson.annotations.SerializedName


data class AddShopResponse(
    @SerializedName("body")
    var body: Body,
    @SerializedName("code")
    var code: Int, // 200
    @SerializedName("message")
    var message: String, // shop added successfully
    @SerializedName("success")
    var success: Boolean // true
) {
    data class Body(
        @SerializedName("abn")
        var abn: String,
        @SerializedName("accountHolderName")
        var accountHolderName: String,
        @SerializedName("accountNumber")
        var accountNumber: String,
        @SerializedName("approvalStatus")
        var approvalStatus: Int, // 0
        @SerializedName("approvalStatusReason")
        var approvalStatusReason: String,
        @SerializedName("bankAddress")
        var bankAddress: String,
        @SerializedName("bankBranch")
        var bankBranch: String,
        @SerializedName("bankName")
        var bankName: String,
        @SerializedName("bsbNumber")
        var bsbNumber: String,
        @SerializedName("buildingNumber")
        var buildingNumber: String,
        @SerializedName("city")
        var city: String,
        @SerializedName("country")
        var country: String,
        @SerializedName("created")
        var created: Int, // 1644225651
        @SerializedName("createdAt")
        var createdAt: String, // 2022-02-07T09:20:50.000Z
        @SerializedName("deliveriesPerDay")
        var deliveriesPerDay: Int, // 0
        @SerializedName("deliveryPolicy")
        var deliveryPolicy: String,
        @SerializedName("geoLocation")
        var geoLocation: String,
        @SerializedName("homeDelivery")
        var homeDelivery: Int, // 0
        @SerializedName("id")
        var id: Int, // 65
        @SerializedName("ifscSwiftCode")
        var ifscSwiftCode: String,
        @SerializedName("image")
        var image: String, // fcfc67f8-b84e-4d61-91ec-1af1fc08b654.jpg
        @SerializedName("latitude")
        var latitude: String, // 30.71450000
        @SerializedName("longitude")
        var longitude: String, // 76.71490000
        @SerializedName("name")
        var name: String, // Seller
        @SerializedName("paymentPolicy")
        var paymentPolicy: String,
        @SerializedName("phone")
        var phone: String,
        @SerializedName("postalCode")
        var postalCode: String,
        @SerializedName("sellerInformation")
        var sellerInformation: String,
        @SerializedName("shopAddress")
        var shopAddress: String, // mohali
        @SerializedName("shop_categories")
        var shopCategories: ArrayList<ShopCategory>,
        @SerializedName("shopCategory")
        var shopCategory: String,
        @SerializedName("shopCloseTime")
        var shopCloseTime: String,
        @SerializedName("shopDescription")
        var shopDescription: String, // visit
        @SerializedName("shopLogo")
        var shopLogo: String,
        @SerializedName("shopName")
        var shopName: String, // collection
        @SerializedName("shopOpenTime")
        var shopOpenTime: String,
        @SerializedName("state")
        var state: String,
        @SerializedName("streetNumber")
        var streetNumber: String,
        @SerializedName("taxInPercent")
        var taxInPercent: Int, // 0
        @SerializedName("taxValue")
        var taxValue: Int, // 0
        @SerializedName("updated")
        var updated: Int, // 1644225651
        @SerializedName("updatedAt")
        var updatedAt: String, // 2022-02-07T12:09:05.000Z
        @SerializedName("userId")
        var userId: Int, // 263
        @SerializedName("year")
        var year: Int // 2000
    ) {
        data class ShopCategory(
            @SerializedName("categoryId")
            var categoryId: Int, // 10
            @SerializedName("createdAt")
            var createdAt: String, // 2022-02-07T12:09:05.000Z
            @SerializedName("id")
            var id: Int, // 144
            @SerializedName("image")
            var image: String, // 6af829e5-5b5c-4d50-adf4-64e4709a9a45.jpg
            @SerializedName("name")
            var name: String, // qwerty
            @SerializedName("shopId")
            var shopId: Int, // 263
            @SerializedName("updatedAt")
            var updatedAt: String // 2022-02-07T12:09:05.000Z
        )
    }
}