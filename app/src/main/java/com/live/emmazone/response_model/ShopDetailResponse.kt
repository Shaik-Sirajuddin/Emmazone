package com.live.emmazone.response_model


import com.google.gson.annotations.SerializedName

data class ShopDetailResponse(
    @SerializedName("body")
    var body: Body,
    @SerializedName("code")
    var code: Int, // 200
    @SerializedName("message")
    var message: String, // Shop Details
    @SerializedName("success")
    var success: Boolean // true
) {
    data class Body(
        @SerializedName("distance")
        var distance: Int, // 5430
        @SerializedName("id")
        var id: Int, // 97
        @SerializedName("image")
        var image: String, // 0e7b1969-7b06-48a9-bd2e-6811307a9304.jpg
        @SerializedName("isLiked")
        var isLiked: Int, // 0
        @SerializedName("latitude")
        var latitude: String, // 30.71402850
        @SerializedName("longitude")
        var longitude: String, // 76.69094590
        @SerializedName("products")
        var products: List<Product>,
        @SerializedName("ratings")
        var ratings: String, // 0.0
        @SerializedName("shopAddress")
        var shopAddress: String, // D-199, 5th Floor Phase, 8-B, Industrial Area, Sector 74, Sahibzada Ajit Singh Nagar, Punjab 160071, India
        @SerializedName("shop_categories")
        var shopCategories: ArrayList<ShopCategory>,
        @SerializedName("shopDescription")
        var shopDescription: String, // fsf sda df fg
        @SerializedName("shopName")
        var shopName: String, // Cqlsys Tech
        @SerializedName("userId")
        var userId: Int // 295
    ) {
        data class Product(
            @SerializedName("barcodeImage")
            var barcodeImage: String,
            @SerializedName("brandName")
            var brandName: String,
            @SerializedName("category")
            var category: String,
            @SerializedName("categoryId")
            var categoryId: Int, // 22
            @SerializedName("created")
            var created: Int, // 1645171983
            @SerializedName("createdAt")
            var createdAt: String, // 2022-02-18T08:13:03.000Z
            @SerializedName("description")
            var description: String, // dsfsjkf dhfodn jhweo wfoeh
            @SerializedName("id")
            var id: Int, // 90
            @SerializedName("image")
            var image: String,
            @SerializedName("isApproved")
            var isApproved: Int, // 1
            @SerializedName("name")
            var name: String, // Sumsung M10
            @SerializedName("product_color")
            var productColor: String, // 27,
            @SerializedName("product_highlight")
            var productHighlight: Int, // 1
            @SerializedName("product_price")
            var productPrice: String, // 500.00
            @SerializedName("product_quantity")
            var productQuantity: Int, // 6
            @SerializedName("product_type")
            var productType: Int, // 0
            @SerializedName("size")
            var size: String, // 35,37,
            @SerializedName("status")
            var status: Int, // 0
            @SerializedName("taxCategoryId")
            var taxCategoryId: Int, // 0
            @SerializedName("updated")
            var updated: Int, // 1645171983
            @SerializedName("updatedAt")
            var updatedAt: String, // 2022-02-18T08:13:03.000Z
            @SerializedName("userId")
            var userId: Int, // 295
            @SerializedName("vendorId")
            var vendorId: Int // 0
        )

        data class ShopCategory(
            @SerializedName("categoryId")
            var categoryId: Int, // 20
            @SerializedName("createdAt")
            var createdAt: String, // 2022-02-18T08:11:18.000Z
            @SerializedName("id")
            var id: Int, // 171
            @SerializedName("shopId")
            var shopId: Int, // 295
            @SerializedName("updatedAt")
            var updatedAt: String, // 2022-02-18T08:11:18.000Z
            @SerializedName("vendorDetailId")
            var vendorDetailId: Int // 97
        )
    }
}