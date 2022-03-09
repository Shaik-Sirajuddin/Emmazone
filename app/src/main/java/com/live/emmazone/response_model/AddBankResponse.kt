package com.live.emmazone.response_model


import com.google.gson.annotations.SerializedName

data class AddBankResponse(
    @SerializedName("body")
    var body: Body,
    @SerializedName("code")
    var code: Int, // 200
    @SerializedName("message")
    var message: String, // Bank added successfully
    @SerializedName("success")
    var success: Boolean // true
) {
    data class Body(
        @SerializedName("abn")
        var abn: String,
        @SerializedName("accountHolderName")
        var accountHolderName: String, // sachin
        @SerializedName("accountNumber")
        var accountNumber: String, // 0717000102798895
        @SerializedName("approvalStatus")
        var approvalStatus: Int, // 0
        @SerializedName("approvalStatusReason")
        var approvalStatusReason: String,
        @SerializedName("bankAddress")
        var bankAddress: String,
        @SerializedName("bankBranch")
        var bankBranch: String, // hdfc mohali
        @SerializedName("bankName")
        var bankName: String, // HDFC
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
        var ifscSwiftCode: String, // HDFC0071700
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
        var updatedAt: String, // 2022-02-17T07:55:11.000Z
        @SerializedName("userId")
        var userId: Int, // 263
        @SerializedName("year")
        var year: Int // 2000
    )
}