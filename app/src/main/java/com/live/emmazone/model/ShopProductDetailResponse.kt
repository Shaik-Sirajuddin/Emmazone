package com.live.emmazone.model

import java.io.Serializable
import com.google.gson.annotations.SerializedName


data class ShopProductDetailResponse(
    @SerializedName("body")
    var body: Body,
    @SerializedName("code")
    var code: Int, // 200
    @SerializedName("message")
    var message: String, // product Updated successfully
    @SerializedName("success")
    var success: Boolean // true
):Serializable {
    data class Body(
        @SerializedName("cartCount")
        var cartCount: Int, // 1
        @SerializedName("category")
        var category: Category,
        @SerializedName("categoryId")
        var categoryId: Int, // 34
        @SerializedName("created")
        var created: Int, // 1647928878
        @SerializedName("createdAt")
        var createdAt: String, // 2022-03-22T06:01:18.000Z
        @SerializedName("description")
        var description: String, // Good quality, Light weight
        @SerializedName("id")
        var id: Int, // 150
        @SerializedName("mainImage")
        var mainImage: String, // http://202.164.42.227:8188/uploads/product/db8ee817-18f0-454b-b74b-ce399f13ceb4.jpg
        @SerializedName("name")
        var name: String, // Shoes
        @SerializedName("notificationCount")
        var notificationCount: Int, // 2
        @SerializedName("product_highlight")
        var productHighlight: Int, // 1
        @SerializedName("product_images")
        var productImages: ArrayList<ProductImage>,
        @SerializedName("minPrice")
        var productPrice: String, // 600.00
        @SerializedName("productReview")
        var productReview: String, // 0.0
        @SerializedName("shortDescription")
        var shortDescription: String, // Good Quality
        @SerializedName("status")
        var status: Int, // 1
        @SerializedName("taxValue")
        var taxValue: TaxValue?,
        @SerializedName("userId")
        var userId: Int ,// 336
        @SerializedName("latitude")
        var latitude : String,
        @SerializedName("longitude")
        var longitude : String,
        @SerializedName("product_variants")
        var productVariants : ArrayList<ProductVariant>
    ):Serializable {
        data class Category(
            @SerializedName("image")
            var image: String, // http://202.164.42.227:8188/uploads/category/13acda88-d5d3-44d0-8984-99509120a952.jpg
            @SerializedName("name")
            var name: String // Jeans
        ):Serializable
//
//        data class ProductColor(
//            @SerializedName("categoryColorId")
//            var categoryColorId: Int, // 80
//            @SerializedName("color")
//            var color: String, // White
//            @SerializedName("createdAt")
//            var createdAt: String, // 1970-01-20T01:45:28.000Z
//            @SerializedName("id")
//            var id: Int, // 101
//            @SerializedName("productId")
//            var productId: Int, // 150
//            @SerializedName("updatedAt")
//            var updatedAt: String // 1970-01-20T01:45:28.000Z
//        ):Serializable

        data class ProductImage(
            @SerializedName("id")
            var id: Int, // 481
            @SerializedName("image")
            var image: String, // http://202.164.42.227:8188/uploads/product/db8ee817-18f0-454b-b74b-ce399f13ceb4.jpg
            @SerializedName("isMainImage")
            var isMainImage: Int, // 1
            @SerializedName("product_id")
            var productId: Int // 150
        ):Serializable
//
//        data class ProductSize(
//            @SerializedName("categorySizeId")
//            var categorySizeId: Int, // 77
//            @SerializedName("createdAt")
//            var createdAt: String, // 1970-01-20T01:45:28.000Z
//            @SerializedName("id")
//            var id: Int, // 102
//            @SerializedName("productId")
//            var productId: Int, // 150
//            @SerializedName("size")
//            var size: String, // 34inches
//            @SerializedName("updatedAt")
//            var updatedAt: String // 1970-01-20T01:45:28.000Z
//        ):Serializable

        data class TaxValue(
            @SerializedName("comment")
            var comment: String,
            @SerializedName("created")
            var created: Int, // 1592274435
            @SerializedName("createdAt")
            var createdAt: String, // 2020-06-16T02:27:15.000Z
            @SerializedName("id")
            var id: Int, // 18
            @SerializedName("name")
            var name: String, // tax_value
            @SerializedName("updated")
            var updated: Int, // 1592274435
            @SerializedName("updatedAt")
            var updatedAt: String, // 2022-03-02T07:03:20.000Z
            @SerializedName("value")
            var value: String // 5
        ):Serializable


    }
}