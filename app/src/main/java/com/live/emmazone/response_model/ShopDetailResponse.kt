package com.live.emmazone.response_model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ShopDetailResponse(
    @SerializedName("body")
    var body: Body,
    @SerializedName("code")
    var code: Int, // 200
    @SerializedName("message")
    var message: String, // Shop Details
    @SerializedName("success")
    var success: Boolean // true
) :Parcelable{
    @Parcelize
    data class Body(
        @SerializedName("cartCount")
        var cartCount: Int, // 0
        @SerializedName("distance")
        var distance: Int, // 8738
        @SerializedName("id")
        var id: Int, // 121
        @SerializedName("image")
        var image: String, // e4440279-0b7e-4de3-9bff-68905a951de4.jpg
        @SerializedName("isLiked")
        var isLiked: Int, // 1
        @SerializedName("latitude")
        var latitude: String, // 30.71402850
        @SerializedName("longitude")
        var longitude: String, // 76.69094590
        @SerializedName("notificationCount")
        var notificationCount: Int, // 0
        @SerializedName("products")
        var products: ArrayList<SellerShopDetailResponse.Body.ShopDetails.Product>,
        @SerializedName("ratings")
        var ratings: String, // 0.0
        @SerializedName("shopAddress")
        var shopAddress: String, // D-199, 5th Floor Phase, 8-B, Industrial Area, Sector 74, Sahibzada Ajit Singh Nagar, Punjab 160071, India
        @SerializedName("shop_categories")
        var shopCategories: ArrayList<SellerShopDetailResponse.Body.ShopDetails.ShopCategory>,
        @SerializedName("shopDescription")
        var shopDescription: String, // All type garment available
        @SerializedName("shopName")
        var shopName: String, // Cqlsys
        @SerializedName("userId")
        var userId: Int, // 336
        @SerializedName("year")
        var year: Int // 2010
    ) :Parcelable{
        @Parcelize
        data class Product(
            @SerializedName("barcodeImage")
            var barcodeImage: String,
            @SerializedName("brandName")
            var brandName: String,
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
            @SerializedName("image")
            var image: String,
            @SerializedName("isApproved")
            var isApproved: Int, // 1
            @SerializedName("name")
            var name: String, // Shoes
            @SerializedName("product_color")
            var productColor: String, // 0
            @SerializedName("product_highlight")
            var productHighlight: Int, // 1
            @SerializedName("product_images")
            var productImages: List<ProductImage>,
            @SerializedName("product_price")
            var productPrice: String, // 600.00
            @SerializedName("product_quantity")
            var productQuantity: Int, // 10
            @SerializedName("product_type")
            var productType: Int, // 0
            @SerializedName("shortDescription")
            var shortDescription: String, // Good Quality
            @SerializedName("size")
            var size: Int, // 0
            @SerializedName("status")
            var status: Int, // 1
            @SerializedName("taxCategoryId")
            var taxCategoryId: Int, // 0
            @SerializedName("updated")
            var updated: Int, // 1647928878
            @SerializedName("updatedAt")
            var updatedAt: String, // 2022-03-22T06:01:18.000Z
            @SerializedName("userId")
            var userId: Int, // 336
            @SerializedName("vendorId")
            var vendorId: Int // 0
        ) :Parcelable{
            @Parcelize
            data class ProductImage(
                @SerializedName("createdAt")
                var createdAt: Int, // 0
                @SerializedName("id")
                var id: Int, // 480
                @SerializedName("image")
                var image: String, // 47713944-4918-491f-962f-09b4f35e3237.jpg
                @SerializedName("isMainImage")
                var isMainImage: Int, // 0
                @SerializedName("product_id")
                var productId: Int, // 150
                @SerializedName("status")
                var status: Int, // 1
                @SerializedName("updatedAt")
                var updatedAt: Int // 0
            ):Parcelable
        }

        @Parcelize
        data class ShopCategory(
            @SerializedName("categoryId")
            var categoryId: Int, // 32
            @SerializedName("categoryImage")
            var categoryImage: String, // http://202.164.42.227:8188/uploads/category/3813de02-87bd-4229-b3e8-b1f3d4b92643.jpg
            @SerializedName("categoryName")
            var categoryName: String, // Shoes
            @SerializedName("createdAt")
            var createdAt: String, // 2022-03-22T11:13:53.000Z
            @SerializedName("id")
            var id: Int, // 335
            @SerializedName("shopId")
            var shopId: Int, // 336
            @SerializedName("updatedAt")
            var updatedAt: String, // 2022-03-22T11:13:53.000Z
            @SerializedName("vendorDetailId")
            var vendorDetailId: Int // 121
        ):Parcelable
    }
}