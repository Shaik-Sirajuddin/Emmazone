package com.live.emmazone.response_model


import com.google.gson.annotations.SerializedName

data class MyEarningResponse(
    @SerializedName("body")
    var body: Body,
    @SerializedName("code")
    var code: Int, // 200
    @SerializedName("message")
    var message: String, // Earnings List fetched successfully
    @SerializedName("success")
    var success: Boolean // true
) {
    data class Body(
        @SerializedName("findOrder")
        var findOrder: ArrayList<FindOrder>,
        @SerializedName("total_earning")
        var totalEarning: Int, // 2730
        @SerializedName("total_product")
        var totalProduct: Int // 2
    ) {
        data class FindOrder(
            @SerializedName("adminCommission")
            var adminCommission: String, // 0.00
            @SerializedName("created")
            var created: Int, // 1648707159
            @SerializedName("createdAt")
            var createdAt: String, // 2022-03-31T06:12:38.000Z
            @SerializedName("customerId")
            var customerId: Int, // 335
            @SerializedName("deliveryType")
            var deliveryType: Int, // 0
            @SerializedName("id")
            var id: Int, // 1
            @SerializedName("netAmount")
            var netAmount: String, // 600.00
            @SerializedName("orderJson")
            var orderJson: String, // {"paymentMethod":"2","payment":{},"userAddress":{},"orderItems":[{"id":150,"userId":336,"status":1,"name":"Shoes","product_quantity":10,"product_highlight":1,"product_price":"600.00","categoryId":34,"categoryColorId":80,"categorySizeId":77,"description":"Good quality, Light weight","productReview":"0.0","mainImage":"http://202.164.42.227:8188/uploads/product/db8ee817-18f0-454b-b74b-ce399f13ceb4.jpg","created":1647928878,"createdAt":"2022-03-22T06:01:18.000Z","vendor":{"id":336,"username":"Seller","image":"http://202.164.42.227:8188/uploads/user/e4440279-0b7e-4de3-9bff-68905a951de4.jpg"},"product_images":[{"id":481,"product_id":150,"isMainImage":1,"image":"http://202.164.42.227:8188/uploads/product/db8ee817-18f0-454b-b74b-ce399f13ceb4.jpg"},{"id":480,"product_id":150,"isMainImage":0,"image":"http://202.164.42.227:8188/uploads/product/47713944-4918-491f-962f-09b4f35e3237.jpg"}],"vendorId":336,"orderedQty":"1","orderedQtyPrice":600}]}
            @SerializedName("orderNo")
            var orderNo: String, // 1648-707158-9411
            @SerializedName("orderStatus")
            var orderStatus: Int, // 2
            @SerializedName("paymentMethod")
            var paymentMethod: Int, // 2
            @SerializedName("productName")
            var productName: String,
            @SerializedName("qrCode")
            var qrCode: String, // https://api.qrserver.com/v1/create-qr-code/?size=100x100&data=1
            @SerializedName("qty")
            var qty: Int, // 1
            @SerializedName("shippingCharges")
            var shippingCharges: String, // 0.00
            @SerializedName("taxCharged")
            var taxCharged: String, // 30.00
            @SerializedName("total")
            var total: String, // 630.00
            @SerializedName("updated")
            var updated: Int, // 1648707159
            @SerializedName("updatedAt")
            var updatedAt: String, // 2022-03-31T06:17:07.000Z
            @SerializedName("userAddressId")
            var userAddressId: Int, // 0
            @SerializedName("vendorId")
            var vendorId: Int // 336
        )
    }
}