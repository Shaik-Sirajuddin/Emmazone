package com.live.emmazone.model

import com.live.emmazone.model.sellerShopDetailNew.ProductImage
import com.live.emmazone.response_model.ShopDetailResponse
import com.google.gson.annotations.SerializedName


data class CartResponsModel(
    @SerializedName("body")
    var body: Body,
    @SerializedName("code")
    var code: Int, // 200
    @SerializedName("message")
    var message: String, // Cart Items listing fetched successfully.
    @SerializedName("success")
    var success: Boolean // true
) {
    data class Body(
        @SerializedName("cartItems")
        var cartItems: ArrayList<CartItem>,
        @SerializedName("deliveryCharge")
        var deliveryCharge: Int, // 10
        @SerializedName("subTotal")
        var subTotal: Int, // 200
        @SerializedName("tax")
        var tax: Int, // 5
        @SerializedName("taxType")
        var taxType: Int, // 1
        @SerializedName("total")
        var total: Double, // 220.5
        @SerializedName("walletBalance")
        var walletBalance: String, // 0.00
        @SerializedName("youMayLikeProducts")
        var youMayLikeProducts: ArrayList<YouMayLikeProduct>
    ) {
        data class CartItem(
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
            var qty: Int, // 4
            @SerializedName("totalQtyPriceForThisProduct")
            var totalQtyPriceForThisProduct: Int, // 200
            @SerializedName("updated")
            var updated: Int, // 1646116361
            @SerializedName("updatedAt")
            var updatedAt: String, // 2022-03-01T06:32:41.000Z
            @SerializedName("userId")
            var userId: Int // 267
        ) {
            data class Product(
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
            )
        }

        data class YouMayLikeProduct(
            @SerializedName("category")
            var category: Category,
            @SerializedName("categoryColorId")
            var categoryColorId: Int, // 27
            @SerializedName("categoryId")
            var categoryId: Int, // 22
            @SerializedName("categorySizeId")
            var categorySizeId: Int, // 35
            @SerializedName("created")
            var created: Int, // 1645706739
            @SerializedName("createdAt")
            var createdAt: String, // 2022-02-24T12:45:38.000Z
            @SerializedName("description")
            var description: String, // nice shoes
            @SerializedName("id")
            var id: Int, // 112
            @SerializedName("mainImage")
            var mainImage: String, // http://202.164.42.227:8188/uploads/product/3f14d3a1-61cc-457d-8c76-600212787a1a.jpg
            @SerializedName("name")
            var name: String, // shoes123
            @SerializedName("productColor")
            var productColor: ProductColor,
            @SerializedName("product_highlight")
            var productHighlight: Int, // 1
            @SerializedName("product_images")
            var productImages: List<Any>,
            @SerializedName("product_price")
            var productPrice: String, // 1200.00
            @SerializedName("product_quantity")
            var productQuantity: Int, // 1
            @SerializedName("productReview")
            var productReview: String, // 0.0
            @SerializedName("product_size")
            var productSize: ProductSize,
            @SerializedName("status")
            var status: Int, // 0
            @SerializedName("userId")
            var userId: Int // 299
        ) {
            data class Category(
                @SerializedName("image")
                var image: String, // http://202.164.42.227:8188/uploads/category/b3987c05-2164-42c0-89b4-9a99c48d94c0.jpg
                @SerializedName("name")
                var name: String // Mobiles
            )

            data class ProductColor(
                @SerializedName("categoryColorId")
                var categoryColorId: Int, // 27
                @SerializedName("color")
                var color: String, // Black
                @SerializedName("createdAt")
                var createdAt: String, // 1970-01-20T01:09:41.000Z
                @SerializedName("id")
                var id: Int, // 44
                @SerializedName("productId")
                var productId: Int, // 112
                @SerializedName("updatedAt")
                var updatedAt: String // 1970-01-20T01:09:41.000Z
            )

            data class ProductSize(
                @SerializedName("categorySizeId")
                var categorySizeId: Int, // 35
                @SerializedName("createdAt")
                var createdAt: String, // 1970-01-20T01:09:41.000Z
                @SerializedName("id")
                var id: Int, // 45
                @SerializedName("productId")
                var productId: Int, // 112
                @SerializedName("size")
                var size: String, // 5 inches
                @SerializedName("updatedAt")
                var updatedAt: String // 1970-01-20T01:09:41.000Z
            )
        }
    }
}