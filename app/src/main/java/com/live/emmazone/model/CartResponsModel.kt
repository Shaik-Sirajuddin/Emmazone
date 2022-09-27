package com.live.emmazone.model

import com.google.gson.annotations.SerializedName
import com.live.emmazone.response_model.Product
import com.live.emmazone.response_model.SearchProductResponse


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
        var cartItems: List<CartItem>,
        @SerializedName("deliveryCharge")
        var deliveryCharge: Int, // 10
        @SerializedName("subTotal")
        var subTotal: Double, // 1601.38
        @SerializedName("tax")
        var tax: Int, // 5
        @SerializedName("taxType")
        var taxType: Int, // 1
        @SerializedName("total")
        var total: Double, // 1691.949
        @SerializedName("walletBalance")
        var walletBalance: String, // 0.00
        @SerializedName("youMayLikeProducts")
        var youMayLikeProducts: List<YouMayLikeProduct>
    ) {
        data class CartItem(
            @SerializedName("created")
            var created: Int, // 1648552687
            @SerializedName("createdAt")
            var createdAt: String, // 2022-03-29T11:18:07.000Z
            @SerializedName("id")
            var id: Int, // 126
            @SerializedName("product")
            var product: Product,
            @SerializedName("productId")
            var productId: Int, // 155
            @SerializedName("qty")
            var qty: Int, // 2
            @SerializedName("totalQtyPriceForThisProduct")
            var totalQtyPriceForThisProduct: Double, // 1001.38
            @SerializedName("updated")
            var updated: Int, // 1648552687
            @SerializedName("updatedAt")
            var updatedAt: String, // 2022-03-29T11:18:07.000Z
            @SerializedName("userId")
            var userId: Int // 335
        ) {
            data class Product(
                @SerializedName("categoryColorId")
                var categoryColorId: Int, // 81
                @SerializedName("categoryId")
                var categoryId: Int, // 32
                @SerializedName("categorySizeId")
                var categorySizeId: Int, // 79
                @SerializedName("created")
                var created: Int, // 1648552308
                @SerializedName("createdAt")
                var createdAt: String, // 2022-03-29T11:11:48.000Z
                @SerializedName("description")
                var description: String, // giiggg ggn vv  gg  f gggggggccffnkj gggafgg tttt yomnhyddkkjccg wsfgf gfdgds ggas fbgbfsfgdaadgsfgda sfgafgag sgaas sgdasgda sgfdag gsasag sga gsda sgad sgad gererqrtq et wge
                @SerializedName("id")
                var id: Int, // 155
                @SerializedName("mainImage")
                var mainImage: String?, // http://202.164.42.227:8188/uploads/product/72932e18-f10b-439f-9fa4-853ffd86c7f3.jpg
                @SerializedName("name")
                var name: String, // New
                @SerializedName("product_highlight")
                var productHighlight: Int, // 1
                @SerializedName("product_price")
                var productPrice: String, // 500.69
                @SerializedName("product_quantity")
                var productQuantity: Int, // 15
                @SerializedName("productReview")
                var productReview: String, // 0.0
                @SerializedName("status")
                var status: Int, // 1
                @SerializedName("userId")
                var userId: Int, // 336
                @SerializedName("vendor")
                var vendor: Vendor?,
                @SerializedName("product_images")
                var images : ArrayList<SearchProductResponse.Body.ProductImage>?,
                @SerializedName("product_group")
                var group : SearchProductResponse.Body.Group?
            ) {
                data class Vendor(
                    @SerializedName("id")
                    var id: Int, // 336
                    @SerializedName("image")
                    var image: String, // http://202.164.42.227:8188/uploads/user/e4440279-0b7e-4de3-9bff-68905a951de4.jpg
                    @SerializedName("username")
                    var username: String // Seller
                )
            }
        }

        data class YouMayLikeProduct(
            @SerializedName("category")
            var category: Category,
            @SerializedName("categoryColorId")
            var categoryColorId: Int, // 83
            @SerializedName("categoryId")
            var categoryId: Int, // 32
            @SerializedName("categorySizeId")
            var categorySizeId: Int, // 79
            @SerializedName("created")
            var created: Int, // 1647927669
            @SerializedName("createdAt")
            var createdAt: String, // 2022-03-22T05:41:08.000Z
            @SerializedName("description")
            var description: String, // Let's try
            @SerializedName("id")
            var id: Int, // 149
            @SerializedName("mainImage")
            var mainImage: String, // http://202.164.42.227:8188/uploads/product/7148cfe7-2744-4a31-b8ef-574e433bb2ee.jpg
            @SerializedName("name")
            var name: String, // Nike Shoes
            @SerializedName("productColor")
            var productColor: ProductColor,
            @SerializedName("product_highlight")
            var productHighlight: Int, // 1
            @SerializedName("product_images")
            var productImages: ArrayList<Product.ProductImage>,
            @SerializedName("product_price")
            var productPrice: String, // 599.00
            @SerializedName("product_quantity")
            var productQuantity: Int, // 8
            @SerializedName("productReview")
            var productReview: String, // 0.0
            @SerializedName("product_size")
            var productSize: ProductSize,
            @SerializedName("status")
            var status: Int, // 1
            @SerializedName("userId")
            var userId: Int, // 334
            @SerializedName("product_group")
            var group : SearchProductResponse.Body.Group?
        ) {
            data class Category(
                @SerializedName("image")
                var image: String, // http://202.164.42.227:8188/uploads/category/3813de02-87bd-4229-b3e8-b1f3d4b92643.jpg
                @SerializedName("name")
                var name: String // Shoes
            )

            data class ProductColor(
                @SerializedName("categoryColorId")
                var categoryColorId: Int, // 83
                @SerializedName("color")
                var color: String, // Black
                @SerializedName("createdAt")
                var createdAt: String, // 1970-01-20T01:45:27.000Z
                @SerializedName("id")
                var id: Int, // 100
                @SerializedName("productId")
                var productId: Int, // 149
                @SerializedName("updatedAt")
                var updatedAt: String // 1970-01-20T01:45:27.000Z
            )

            data class ProductImage(
                @SerializedName("id")
                var id: Int, // 477
                @SerializedName("image")
                var image: String, // http://202.164.42.227:8188/uploads/product/6cdff530-c645-4e34-815e-4376258d46e8.jpg
                @SerializedName("isMainImage")
                var isMainImage: Int, // 0
                @SerializedName("product_id")
                var productId: Int // 149
            )

            data class ProductSize(
                @SerializedName("categorySizeId")
                var categorySizeId: Int, // 79
                @SerializedName("createdAt")
                var createdAt: String, // 1970-01-20T01:45:27.000Z
                @SerializedName("id")
                var id: Int, // 101
                @SerializedName("productId")
                var productId: Int, // 149
                @SerializedName("size")
                var size: String, // 8inches
                @SerializedName("updatedAt")
                var updatedAt: String // 1970-01-20T01:45:27.000Z
            )
        }
    }
}