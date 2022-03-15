package com.live.emmazone.response_model
import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class SellerShopDetailResponse(
    @SerializedName("body")
    val body: Body,
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
):Serializable {
    data class Body(
        @SerializedName("notificationCount")
        val notificationCount: Int,
        @SerializedName("ShopDetails")
        val shopDetails: ShopDetails
    ):Serializable {
        data class ShopDetails(
            @SerializedName("id")
            val id: Int,
            @SerializedName("image")
            val image: String,
            @SerializedName("latitude")
            val latitude: String,
            @SerializedName("likesCount")
            val likesCount: Int,
            @SerializedName("longitude")
            val longitude: String,
            @SerializedName("notificationCount")
            val notificationCount: Int,
            @SerializedName("products")
            val products: ArrayList<Product>,
            @SerializedName("shopAddress")
            val shopAddress: String,
            @SerializedName("shop_categories")
            val shopCategories: ArrayList<ShopCategory>,
            @SerializedName("shopDescription")
            val shopDescription: String,
            @SerializedName("shopName")
            val shopName: String,
            @SerializedName("userId")
            val userId: Int,
            @SerializedName("year")
            val year: Int,
            @SerializedName("isLiked")
            val isLiked: Int,
            @SerializedName("ratings")
            val ratings: String ,
            @SerializedName("distance")
            val distance: Int
        ) :Serializable{
            data class Product(
                @SerializedName("category")
                val category: Category,
                @SerializedName("categoryColorId")
                val categoryColorId: Int,
                @SerializedName("categoryId")
                val categoryId: Int,
                @SerializedName("categorySizeId")
                val categorySizeId: Int,
                @SerializedName("created")
                val created: Int,
                @SerializedName("createdAt")
                val createdAt: String,
                @SerializedName("description")
                val description: String,
                @SerializedName("id")
                val id: Int,
                @SerializedName("mainImage")
                val mainImage: String,
                @SerializedName("name")
                val name: String,
                @SerializedName("product_ highlight")
                val productHighlight: Int,
                @SerializedName("product_images")
                val productImages: List<ProductImage>,
                @SerializedName("product_price")
                val productPrice: String,
                @SerializedName("shortDescription")
                val shortDescription: String,
                @SerializedName("product_quantity")
                val productQuantity: Int,
                @SerializedName("productReview")
                val productReview: String,
                @SerializedName("status")
                val status: Int,
                @SerializedName("userId")
                val userId: Int
            ):Serializable {
                data class Category(
                    @SerializedName("image")
                    val image: String,
                    @SerializedName("name")
                    val name: String
                ):Serializable

                data class ProductImage(
                    @SerializedName("id")
                    val id: Int,
                    @SerializedName("image")
                    val image: String,
                    @SerializedName("isMainImage")
                    val isMainImage: Int,
                    @SerializedName("product_id")
                    val productId: Int
                ):Serializable
            }

            data class ShopCategory(
                @SerializedName("categoryId")
                val categoryId: Int,
                @SerializedName("categoryImage")
                val categoryImage: String,
                @SerializedName("categoryName")
                val categoryName: String,
                @SerializedName("createdAt")
                val createdAt: String,
                @SerializedName("id")
                val id: Int,
                @SerializedName("shopId")
                val shopId: Int,
                @SerializedName("updatedAt")
                val updatedAt: String,
                @SerializedName("vendorDetailId")
                val vendorDetailId: Int
            ):Serializable
        }
    }
}