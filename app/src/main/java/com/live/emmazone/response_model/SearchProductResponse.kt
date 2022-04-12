package com.live.emmazone.response_model


import com.google.gson.annotations.SerializedName

data class SearchProductResponse(
    @SerializedName("body")
    var body: ArrayList<Body>,
    @SerializedName("code")
    var code: Int, // 200
    @SerializedName("message")
    var message: String, // Products listed successfully
    @SerializedName("success")
    var success: Boolean // true
) {
    data class Body(
        @SerializedName("barcodeImage")
        var barcodeImage: String,
        @SerializedName("brandName")
        var brandName: String,
        @SerializedName("categoryColorId")
        var categoryColorId: Int, // 73
        @SerializedName("categoryId")
        var categoryId: Int, // 33
        @SerializedName("categorySizeId")
        var categorySizeId: Int, // 69
        @SerializedName("created")
        var created: Int, // 1648026658
        @SerializedName("createdAt")
        var createdAt: String, // 2022-03-23T09:10:57.000Z
        @SerializedName("description")
        var description: String, // Good Product, 10 Yr. Warrenty
        @SerializedName("id")
        var id: Int, // 152
        @SerializedName("image")
        var image: String,
        @SerializedName("isApproved")
        var isApproved: Int, // 1
        @SerializedName("name")
        var name: String, // TV
        @SerializedName("product_color")
        var productColor: String, // 0
        @SerializedName("product_highlight")
        var productHighlight: Int, // 0
        @SerializedName("product_images")
        var productImages: List<ProductImage>,
        @SerializedName("product_price")
        var productPrice: String, // 2000.00
        @SerializedName("product_quantity")
        var productQuantity: Int, // 50
        @SerializedName("productReview")
        var productReview: String, // 0.0
        @SerializedName("product_type")
        var productType: Int, // 0
        @SerializedName("shortDescription")
        var shortDescription: String, // Good TV
        @SerializedName("size")
        var size: Int, // 0
        @SerializedName("status")
        var status: Int, // 1
        @SerializedName("taxCategoryId")
        var taxCategoryId: Int, // 0
        @SerializedName("updated")
        var updated: Int, // 1648026658
        @SerializedName("updatedAt")
        var updatedAt: String, // 2022-03-23T09:10:57.000Z
        @SerializedName("userId")
        var userId: Int, // 336
        @SerializedName("vendorDetail")
        var vendorDetail: VendorDetail,
        @SerializedName("vendorId")
        var vendorId: Int // 0
    ) {
        data class ProductImage(
            @SerializedName("id")
            var id: Int, // 484
            @SerializedName("image")
            var image: String, // http://202.164.42.227:8188/uploads/product/b534158a-aa1f-4b69-b19d-1ca1e8ae46c3.jpg
            @SerializedName("isMainImage")
            var isMainImage: Int, // 0
            @SerializedName("product_id")
            var productId: Int // 152
        )

        data class VendorDetail(
            @SerializedName("id")
            var id: Int, // 121
            @SerializedName("image")
            var image: String, // http://202.164.42.227:8188/uploads/vendorDetail/e4440279-0b7e-4de3-9bff-68905a951de4.jpg
            @SerializedName("shopName")
            var shopName: String // Cqlsys
        )
    }
}