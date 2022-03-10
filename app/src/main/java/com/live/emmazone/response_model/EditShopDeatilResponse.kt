package com.live.emmazone.response_model


import com.google.gson.annotations.SerializedName

data class EditShopDeatilResponse(
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
        var created: Int, // 1645517163
        @SerializedName("createdAt")
        var createdAt: String, // 2022-02-22T08:06:02.000Z
        @SerializedName("deliveriesPerDay")
        var deliveriesPerDay: Int, // 0
        @SerializedName("deliveryPolicy")
        var deliveryPolicy: String,
        @SerializedName("geoLocation")
        var geoLocation: String,
        @SerializedName("homeDelivery")
        var homeDelivery: Int, // 0
        @SerializedName("id")
        var id: Int, // 101
        @SerializedName("ifscSwiftCode")
        var ifscSwiftCode: String,
        @SerializedName("image")
        var image: String, // bf2ab579-f34d-4d5c-9469-fafb00b9e882.jpg
        @SerializedName("latitude")
        var latitude: String, // 30.71402850
        @SerializedName("longitude")
        var longitude: String, // 76.69094590
        @SerializedName("name")
        var name: String, // new
        @SerializedName("paymentPolicy")
        var paymentPolicy: String,
        @SerializedName("phone")
        var phone: String,
        @SerializedName("postalCode")
        var postalCode: String,
        @SerializedName("sellerInformation")
        var sellerInformation: String,
        @SerializedName("shopAddress")
        var shopAddress: String, // Mohali
        @SerializedName("shop_categories")
        var shopCategories: List<ShopCategory>,
        @SerializedName("shopCategory")
        var shopCategory: String,
        @SerializedName("shopCloseTime")
        var shopCloseTime: String,
        @SerializedName("shopDescription")
        var shopDescription: String, // baout to rise
        @SerializedName("shopLogo")
        var shopLogo: String,
        @SerializedName("shopName")
        var shopName: String, // Rat Collection
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
        var updated: Int, // 1645517163
        @SerializedName("updatedAt")
        var updatedAt: String, // 2022-03-01T12:21:53.000Z
        @SerializedName("userId")
        var userId: Int, // 299
        @SerializedName("year")
        var year: Int // 1998
    ) {
        data class ShopCategory(
            @SerializedName("categoryId")
            var categoryId: Int, // 21
            @SerializedName("createdAt")
            var createdAt: String, // 2022-03-01T12:21:53.000Z
            @SerializedName("id")
            var id: Int, // 195
            @SerializedName("image")
            var image: String, // e2de3eb9-c82f-4906-a4a7-5e8aa8ae4362.jpg
            @SerializedName("name")
            var name: String, // Pants
            @SerializedName("shopId")
            var shopId: Int, // 299
            @SerializedName("updatedAt")
            var updatedAt: String, // 2022-03-01T12:21:53.000Z
            @SerializedName("vendorDetailId")
            var vendorDetailId: Int // 101
        )
    }
}