package com.live.emmazone.response_model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Product(
    @SerializedName("category")
    var category: Category,
    @SerializedName("categoryColorId")
    var categoryColorId: Int, // 80
    @SerializedName("categoryId")
    var categoryId: Int, // 34
    @SerializedName("categorySizeId")
    var categorySizeId: Int, // 77
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
    @SerializedName("userId")
    var userId: Int, // 336
    @SerializedName("productColor")
    var productColor: ProductColor,
    @SerializedName("product_highlight")
    var productHighlight: Int, // 1
    @SerializedName("product_images")
    var images: ArrayList<ProductImage>,
    @SerializedName("product_price")
    var productPrice: String, // 600.00
    @SerializedName("product_quantity")
    var productQuantity: Int, // 10
    @SerializedName("productReview")
    var productReview: String, // 0.0
    @SerializedName("product_size")
    var productSize: ProductSize,
    @SerializedName("shortDescription")
    var shortDescription: String, // Good Quality
    @SerializedName("status")
    var status: Int, // 1
    @SerializedName("isLiked")
    var isLiked : Int ,
    @SerializedName("product_group")
    var group : Group?
 ):Serializable{

    data class Category(
        @SerializedName("image")
        var image: String, // http://202.164.42.227:8188/uploads/category/13acda88-d5d3-44d0-8984-99509120a952.jpg
        @SerializedName("name")
        var name: String // Jeans
    ):Serializable

    data class Group(
        @SerializedName("id")
        var id : Int,
        @SerializedName("product_images")
        var productImages: ArrayList<ProductImage>,
        ):Serializable
    data class ProductColor(
        @SerializedName("categoryColorId")
        var categoryColorId: Int, // 80
        @SerializedName("color")
        var color: String, // White
        @SerializedName("createdAt")
        var createdAt: String, // 1970-01-20T01:45:28.000Z
        @SerializedName("id")
        var id: Int, // 101
        @SerializedName("productId")
        var productId: Int, // 150
        @SerializedName("updatedAt")
        var updatedAt: String // 1970-01-20T01:45:28.000Z
    ):Serializable

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

    data class ProductSize(
        @SerializedName("categorySizeId")
        var categorySizeId: Int, // 77
        @SerializedName("createdAt")
        var createdAt: String, // 1970-01-20T01:45:28.000Z
        @SerializedName("id")
        var id: Int, // 102
        @SerializedName("productId")
        var productId: Int, // 150
        @SerializedName("size")
        var size: String, // 34inches
        @SerializedName("updatedAt")
        var updatedAt: String // 1970-01-20T01:45:28.000Z
    ):Serializable
}
