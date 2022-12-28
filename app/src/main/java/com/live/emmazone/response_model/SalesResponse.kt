package com.live.emmazone.response_model

import java.io.Serializable
import com.google.gson.annotations.SerializedName


data class SalesResponse(
    @SerializedName("body")
    var body: Body,
    @SerializedName("code")
    var code: Int, // 200
    @SerializedName("message")
    var message: String, // Status fetched successfully
    @SerializedName("success")
    var success: Boolean // true
) : Serializable {
    data class Body(
        @SerializedName("notificationCount")
        var notificationCount: Int, // 3
        @SerializedName("response")
        var response: List<Response>
    ) : Serializable {
        data class Response(
            @SerializedName("adminCommission")
            var adminCommission: String, // 0.00
            @SerializedName("created")
            var created: Int, // 1647337716
            @SerializedName("createdAt")
            var createdAt: String, // 2022-03-15T09:48:36.000Z
            @SerializedName("customer")
            var customer: Customer,
            @SerializedName("customerId")
            var customerId: Int, // 267
            @SerializedName("deliveryType")
            var deliveryType: Int, // 2
            @SerializedName("id")
            var id: Int, // 23
            @SerializedName("netAmount")
            var netAmount: String, // 1224.00
            @SerializedName("orderJson")
            var orderJson: OrderJson,
            @SerializedName("orderNo")
            var orderNo: String, // 1647-337716-4631
            @SerializedName("orderStatus")
            var orderStatus: Int, // 0
            @SerializedName("paymentMethod")
            var paymentMethod: Int, // 2
            @SerializedName("productName")
            var productName: String,
            @SerializedName("qrCode")
            var qrCode: String, // https://api.qrserver.com/v1/create-qr-code/?size=100x100&data=23
            @SerializedName("shippingCharges")
            var shippingCharges: String, // 10.00
            @SerializedName("taxCharged")
            var taxCharged: String, // 61.70
            @SerializedName("total")
            var total: String, // 1295.70
            @SerializedName("updated")
            var updated: Int, // 1647337716
            @SerializedName("updatedAt")
            var updatedAt: String, // 2022-03-15T09:48:36.000Z
            @SerializedName("userAddressId")
            var userAddressId: Int, // 19
            @SerializedName("vendor")
            var vendor: Vendor,
            @SerializedName("vendorId")
            var vendorId: Int, // 299,
        ) : Serializable {
            data class Customer(
                @SerializedName("id")
                var id: Int, // 267
                @SerializedName("image")
                var image: String, // http://202.164.42.227:8188/uploads/user/f4859e2f-e933-4a98-a122-c263f9e99b2a.jpg
                @SerializedName("username")
                var username: String // test user
            ) : Serializable

            data class OrderJson(
                @SerializedName("orderItems")
                var orderItems: ArrayList<OrderItem>,
                @SerializedName("payment")
                var payment: Payment,
                @SerializedName("paymentMethod")
                var paymentMethod: String, // 2
                @SerializedName("userAddress")
                var userAddress: UserAddress
            ) : Serializable {
                data class OrderItem(
                    @SerializedName("categoryColorId")
                    var categoryColorId: Int, // 33
                    @SerializedName("categoryId")
                    var categoryId: Int, // 20
                    @SerializedName("categorySizeId")
                    var categorySizeId: Int, // 42
                    @SerializedName("created")
                    var created: Int, // 1647233913
                    @SerializedName("createdAt")
                    var createdAt: String, // 2022-03-14T04:58:32.000Z
                    @SerializedName("description")
                    var description: String, // vzhzh zjzisbakabzzjnsbzhskabhishsushbsusns izjshoabduebisisybd
                    @SerializedName("id")
                    var id: Int, // 144
                    @SerializedName("mainImage")
                    var mainImage: String, // http://202.164.42.227:8188/uploads/product/d2d464d7-73bb-4c54-abc9-11113babe0fb.jpg
                    @SerializedName("name")
                    var name: String, // test short description
                    @SerializedName("orderedQty")
                    var orderedQty: Int, // 2
                    @SerializedName("orderedQtyPrice")
                    var orderedQtyPrice: Double, // 24
                    @SerializedName("product_highlight")
                    var productHighlight: Int, // 1
                    @SerializedName("product_price")
                    var productPrice: String, // 12.00
                    @SerializedName("product_quantity")
                    var productQuantity: Int, // 25
                    @SerializedName("productReview")
                    var productReview: String, // 0.0
                    @SerializedName("status")
                    var status: Int, // 1
                    @SerializedName("userId")
                    var userId: Int, // 299
                    @SerializedName("vendor")
                    var vendor: Vendor,
                    @SerializedName("vendorId")
                    var vendorId: Int,// 299
                    @SerializedName("product")
                    var product :Product?
                ) : Serializable {
                    data class Vendor(
                        @SerializedName("id")
                        var id: Int, // 299
                        @SerializedName("image")
                        var image: String, // http://202.164.42.227:8188/uploads/user/bf2ab579-f34d-4d5c-9469-fafb00b9e882.jpg
                        @SerializedName("username")
                        var username: String // new user
                    ) : Serializable
                    data class  Product(
                        @SerializedName("id")
                        var id: Int, // 299
                        @SerializedName("product_group")
                        var productGroup : ProductGroup?
                    ):Serializable{
                        data class ProductGroup(
                            @SerializedName("registerCode")
                            var registerCode : Int = 0
                        ):Serializable
                    }

                }

                class Payment() : Serializable {

                }

                data class UserAddress(
                    @SerializedName("address")
                    var address: String, // abcdefh jdjd
                    @SerializedName("city")
                    var city: String, // Sahibzada Ajit Singh Nagar
                    @SerializedName("createdAt")
                    var createdAt: String, // 2022-02-11T05:24:10.000Z
                    @SerializedName("id")
                    var id: Int, // 19
                    @SerializedName("latitude")
                    var latitude: Int, // 31
                    @SerializedName("longitude")
                    var longitude: Int, // 77
                    @SerializedName("name")
                    var name: String, // Mani
                    @SerializedName("state")
                    var state: String, // Punjab
                    @SerializedName("updatedAt")
                    var updatedAt: String, // 2022-02-11T05:24:10.000Z
                    @SerializedName("userId")
                    var userId: Int, // 267
                    @SerializedName("zipcode")
                    var zipcode: String // 160071
                ) : Serializable
            }

            data class Vendor(
                @SerializedName("id")
                var id: Int, // 299
                @SerializedName("image")
                var image: String, // http://202.164.42.227:8188/uploads/user/bf2ab579-f34d-4d5c-9469-fafb00b9e882.jpg
                @SerializedName("username")
                var username: String // new user
            ) : Serializable
        }
    }
} 