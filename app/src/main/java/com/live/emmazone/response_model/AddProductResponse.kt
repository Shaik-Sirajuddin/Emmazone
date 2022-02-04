package com.live.emmazone.response_model


import com.google.gson.annotations.SerializedName

data class AddProductResponse(
    @SerializedName("body")
    var body: Body,
    @SerializedName("code")
    var code: Int, // 200
    @SerializedName("message")
    var message: String, // product added successfully
    @SerializedName("success")
    var success: Boolean // true
) {
    data class Body(
        @SerializedName("barcodeImage")
        var barcodeImage: String,
        @SerializedName("brandName")
        var brandName: String,
        @SerializedName("category")
        var category: String,
        @SerializedName("categoryId")
        var categoryId: Int, // 0
        @SerializedName("created")
        var created: Int, // 1643979244
        @SerializedName("createdAt")
        var createdAt: String, // 2022-02-04T12:54:03.000Z
        @SerializedName("description")
        var description: String, // nice shoes
        @SerializedName("id")
        var id: Int, // 81
        @SerializedName("image")
        var image: String,
        @SerializedName("isApproved")
        var isApproved: Int, // 1
        @SerializedName("name")
        var name: String, // shoes
        @SerializedName("product_color")
        var productColor: String, // blue
        @SerializedName("product_highlight")
        var productHighlight: Int, // 1
        @SerializedName("product_price")
        var productPrice: String, // 1200.00
        @SerializedName("product_quantity")
        var productQuantity: Int, // 1
        @SerializedName("product_type")
        var productType: Int, // 1
        @SerializedName("size")
        var size: String, // 13
        @SerializedName("status")
        var status: Int, // 0
        @SerializedName("taxCategoryId")
        var taxCategoryId: Int, // 0
        @SerializedName("updated")
        var updated: Int, // 1643979244
        @SerializedName("updatedAt")
        var updatedAt: String, // 2022-02-04T12:54:03.000Z
        @SerializedName("userId")
        var userId: Int, // 245
        @SerializedName("vendorId")
        var vendorId: Int // 0
    )
}