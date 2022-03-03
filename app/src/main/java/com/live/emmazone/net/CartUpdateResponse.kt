package com.live.emmazone.net
import com.google.gson.annotations.SerializedName


data class CartUpdateResponse(
    @SerializedName("body")
    var body: Body,
    @SerializedName("code")
    var code: Int, // 200
    @SerializedName("message")
    var message: String, // Cart Item updated successfully.
    @SerializedName("success")
    var success: Boolean // true
) {
    data class Body(
        @SerializedName("created")
        var created: Int, // 1646116361
        @SerializedName("createdAt")
        var createdAt: String, // 2022-03-01T06:32:41.000Z
        @SerializedName("id")
        var id: Int, // 10
        @SerializedName("product")
        var product: Product,
        @SerializedName("productId")
        var productId: Int, // 126
        @SerializedName("qty")
        var qty: Int, // 5
        @SerializedName("updated")
        var updated: Int, // 1646283418
        @SerializedName("updatedAt")
        var updatedAt: String, // 2022-03-03T04:56:58.000Z
        @SerializedName("userId")
        var userId: Int // 267
    ) {
        data class Product(
            @SerializedName("category")
            var category: Category,
            @SerializedName("categoryColorId")
            var categoryColorId: Int, // 28
            @SerializedName("categoryId")
            var categoryId: Int, // 22
            @SerializedName("categorySizeId")
            var categorySizeId: Int, // 36
            @SerializedName("created")
            var created: Int, // 1646034073
            @SerializedName("createdAt")
            var createdAt: String, // 2022-02-28T07:41:13.000Z
            @SerializedName("description")
            var description: String, // hdjjd
            @SerializedName("id")
            var id: Int, // 126
            @SerializedName("mainImage")
            var mainImage: String, // http://202.164.42.227:8188/uploads/product/ad1b3d8a-7553-4df9-ac58-d0191ddb161c.jpg
            @SerializedName("name")
            var name: String, // dbbd
            @SerializedName("product_highlight")
            var productHighlight: Int, // 1
            @SerializedName("product_images")
            var productImages: List<ProductImage>,
            @SerializedName("product_price")
            var productPrice: String, // 50.00
            @SerializedName("product_quantity")
            var productQuantity: Int, // 51
            @SerializedName("productReview")
            var productReview: String, // 0.0
            @SerializedName("status")
            var status: Int, // 1
            @SerializedName("userId")
            var userId: Int // 299
        ) {
            data class Category(
                @SerializedName("image")
                var image: String, // http://202.164.42.227:8188/uploads/category/b3987c05-2164-42c0-89b4-9a99c48d94c0.jpg
                @SerializedName("name")
                var name: String // Mobiles
            )

            data class ProductImage(
                @SerializedName("id")
                var id: Int, // 409
                @SerializedName("image")
                var image: String, // http://202.164.42.227:8188/uploads/product/ad1b3d8a-7553-4df9-ac58-d0191ddb161c.jpg
                @SerializedName("isMainImage")
                var isMainImage: Int, // 1
                @SerializedName("product_id")
                var productId: Int // 126
            )
        }
    }
}