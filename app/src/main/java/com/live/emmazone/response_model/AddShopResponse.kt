package com.live.emmazone.response_model

data class AddShopResponse(
    var body: Body,
    var code: Int, // 200
    var message: String, // shop added successfully
    var success: Boolean // true
) {
    data class Body(
        var address: String, // phase 8b
        var createdAt: String, // 2022-01-27T13:00:31.000Z
        var description: String, // here
        var id: Int, // 61
        var latitude: String, // 30.71450000
        var longitude: String, // 76.71490000
        var sellerId: Int, // 248
        var shopName: String, // ghbru collection
        var shop_categories: List<ShopCategory>,
        var updatedAt: String, // 2022-01-27T13:00:31.000Z
        var year_of_foundation: Int // 2001
    ) {
        data class ShopCategory(
            var categoryId: Int, // 11
            var categoryImage: String, // 3de95bbf-1537-4e27-af95-20b4b60a4508.png
            var createdAt: String, // 2022-01-27T13:00:31.000Z
            var id: Int, // 15
            var shopId: Int, // 61
            var updatedAt: String // 2022-01-27T13:00:31.000Z
        )
    }
}