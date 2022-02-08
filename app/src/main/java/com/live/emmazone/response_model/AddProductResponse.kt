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
        var category: String, // TV
        @SerializedName("categoryId")
        var categoryId: Int, // 10
        @SerializedName("created")
        var created: Int, // 1644315106
        @SerializedName("createdAt")
        var createdAt: String, // 2022-02-08T10:11:46.000Z
        @SerializedName("description")
        var description: String, // nice shoes
        @SerializedName("id")
        var id: Int, // 88
        @SerializedName("image")
        var image: String,
        @SerializedName("isApproved")
        var isApproved: Int, // 1
        @SerializedName("name")
        var name: String, // wwwwww
        @SerializedName("product_color")
        var productColor: String, // 1,2,3
        @SerializedName("product_colors")
        var productColors: ArrayList<ProductColor>,
        @SerializedName("product_highlight")
        var productHighlight: Int, // 1
        @SerializedName("product_images")
        var productImages: ArrayList<ProductImage>,
        @SerializedName("product_price")
        var productPrice: String, // 500.00
        @SerializedName("product_quantity")
        var productQuantity: Int, // 1
        @SerializedName("product_sizes")
        var productSizes: ArrayList<ProductSize>,
        @SerializedName("product_type")
        var productType: Int, // 0
        @SerializedName("size")
        var size: String, // 13
        @SerializedName("status")
        var status: Int, // 0
        @SerializedName("taxCategoryId")
        var taxCategoryId: Int, // 0
        @SerializedName("updated")
        var updated: Int, // 1644315106
        @SerializedName("updatedAt")
        var updatedAt: String, // 2022-02-08T10:11:46.000Z
        @SerializedName("userId")
        var userId: Int, // 263
        @SerializedName("vendorId")
        var vendorId: Int // 0
    ) {
        data class ProductColor(
            @SerializedName("color")
            var color: String, // 1
            @SerializedName("id")
            var id: Int, // 5
            @SerializedName("Name")
            var name: String // Red
        )

        data class ProductImage(
            @SerializedName("createdAt")
            var createdAt: Int, // 1644315106
            @SerializedName("id")
            var id: Int, // 342
            @SerializedName("image")
            var image: String, // 6e4d9387-9842-4535-8c30-2487a2ebc7cc.jpg
            @SerializedName("product_id")
            var productId: Int, // 88
            @SerializedName("status")
            var status: Int, // 1
            @SerializedName("updatedAt")
            var updatedAt: Int // 1644315106
        )

        data class ProductSize(
            @SerializedName("createdAt")
            var createdAt: String, // 1970-01-20T00:45:15.000Z
            @SerializedName("id")
            var id: Int, // 5
            @SerializedName("productId")
            var productId: Int, // 88
            @SerializedName("size")
            var size: String, // 13
            @SerializedName("updatedAt")
            var updatedAt: String // 1970-01-20T00:45:15.000Z
        )
    }
}