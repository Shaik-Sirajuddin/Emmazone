package com.live.emmazone.response_model


import com.google.gson.annotations.SerializedName

data class AddOrderResponse(
    @SerializedName("body")
    var body: Body,
    @SerializedName("code")
    var code: Int, // 200
    @SerializedName("message")
    var message: String, // Order created successfully.
    @SerializedName("success")
    var success: Boolean // true
) {
    data class Body(
        @SerializedName("adminCommission")
        var adminCommission: String, // 0.00
        @SerializedName("created")
        var created: Int, // 1648617620
        @SerializedName("createdAt")
        var createdAt: String, // 2022-03-30T05:20:20.000Z
        @SerializedName("customer")
        var customer: Customer,
        @SerializedName("customerId")
        var customerId: Int, // 335
        @SerializedName("deliveryType")
        var deliveryType: Int, // 2
        @SerializedName("id")
        var id: Int, // 70
        @SerializedName("netAmount")
        var netAmount: String, // 600.00
        @SerializedName("orderJson")
        var orderJson: OrderJson,
        @SerializedName("orderNo")
        var orderNo: String, // 1648-617620-4821
        @SerializedName("orderStatus")
        var orderStatus: Int, // 0
        @SerializedName("paymentMethod")
        var paymentMethod: Int, // 1
        @SerializedName("productName")
        var productName: String,
        @SerializedName("qrCode")
        var qrCode: String, // https://api.qrserver.com/v1/create-qr-code/?size=100x100&data=70
        @SerializedName("qty")
        var qty: Int, // 0
        @SerializedName("shippingCharges")
        var shippingCharges: String, // 10.00
        @SerializedName("taxCharged")
        var taxCharged: String, // 30.50
        @SerializedName("total")
        var total: String, // 640.50
        @SerializedName("updated")
        var updated: Int, // 1648617620
        @SerializedName("updatedAt")
        var updatedAt: String, // 2022-03-30T05:20:20.000Z
        @SerializedName("userAddressId")
        var userAddressId: Int, // 31
        @SerializedName("vendorId")
        var vendorId: Int // 336
    ) {
        data class Customer(
            @SerializedName("email")
            var email: String, // user@yopmail.com
            @SerializedName("id")
            var id: Int // 335
        )

        data class OrderJson(
            @SerializedName("orderItems")
            var orderItems: List<OrderItem>,
            @SerializedName("payment")
            var payment: Payment,
            @SerializedName("paymentMethod")
            var paymentMethod: String, // 1
            @SerializedName("userAddress")
            var userAddress: UserAddress
        ) {
            data class OrderItem(
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
                @SerializedName("orderedQty")
                var orderedQty: Int, // 1
                @SerializedName("orderedQtyPrice")
                var orderedQtyPrice: Double, // 600
                @SerializedName("product_highlight")
                var productHighlight: Int, // 1
                @SerializedName("product_price")
                var productPrice: String, // 600.00
                @SerializedName("product_quantity")
                var productQuantity: Int, // 10
                @SerializedName("productReview")
                var productReview: String, // 0.0
                @SerializedName("status")
                var status: Int, // 1
                @SerializedName("userId")
                var userId: Int, // 336
                @SerializedName("vendor")
                var vendor: Vendor,
                @SerializedName("vendorId")
                var vendorId: Int // 336
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

            data class Payment(
                @SerializedName("amount")
                var amount: String, // 64050
                @SerializedName("amount_captured")
                var amountCaptured: Int, // 64050
                @SerializedName("amount_refunded")
                var amountRefunded: String, // 0
                @SerializedName("application")
                var application: Any, // null
                @SerializedName("application_fee")
                var applicationFee: Any, // null
                @SerializedName("application_fee_amount")
                var applicationFeeAmount: Any, // null
                @SerializedName("balance_transaction")
                var balanceTransaction: String, // txn_1KitxAIZXks92CWP8vNXPLxQ
                @SerializedName("billing_details")
                var billingDetails: BillingDetails,
                @SerializedName("calculated_statement_descriptor")
                var calculatedStatementDescriptor: String, // Stripe
                @SerializedName("captured")
                var captured: Boolean, // true
                @SerializedName("created")
                var created: Int, // 1648617619
                @SerializedName("currency")
                var currency: String, // inr
                @SerializedName("customer")
                var customer: Any, // null
                @SerializedName("description")
                var description: String, // Emmazone
                @SerializedName("destination")
                var destination: Any, // null
                @SerializedName("dispute")
                var dispute: Any, // null
                @SerializedName("disputed")
                var disputed: Boolean, // false
                @SerializedName("failure_balance_transaction")
                var failureBalanceTransaction: Any, // null
                @SerializedName("failure_code")
                var failureCode: Any, // null
                @SerializedName("failure_message")
                var failureMessage: Any, // null
                @SerializedName("fraud_details")
                var fraudDetails: FraudDetails,
                @SerializedName("id")
                var id: String, // ch_1Kitx9IZXks92CWPaqDCf5nW
                @SerializedName("invoice")
                var invoice: Any, // null
                @SerializedName("livemode")
                var livemode: Boolean, // false
                @SerializedName("metadata")
                var metadata: Metadata,
                @SerializedName("object")
                var objectX: String, // charge
                @SerializedName("on_behalf_of")
                var onBehalfOf: Any, // null
                @SerializedName("order")
                var order: Any, // null
                @SerializedName("outcome")
                var outcome: Outcome,
                @SerializedName("paid")
                var paid: Boolean, // true
                @SerializedName("payment_intent")
                var paymentIntent: Any, // null
                @SerializedName("payment_method")
                var paymentMethod: String, // card_1Kitx8IZXks92CWP7XyZ6uSU
                @SerializedName("payment_method_details")
                var paymentMethodDetails: PaymentMethodDetails,
                @SerializedName("receipt_email")
                var receiptEmail: Any, // null
                @SerializedName("receipt_number")
                var receiptNumber: Any, // null
                @SerializedName("receipt_url")
                var receiptUrl: String, // https://pay.stripe.com/receipts/acct_1DT5HDIZXks92CWP/ch_1Kitx9IZXks92CWPaqDCf5nW/rcpt_LPjPnUnyAaUjtR7CLsHXzHRgkwIjGKP
                @SerializedName("refunded")
                var refunded: Boolean, // false
                @SerializedName("refunds")
                var refunds: Refunds,
                @SerializedName("review")
                var review: Any, // null
                @SerializedName("shipping")
                var shipping: Any, // null
                @SerializedName("source")
                var source: Source,
                @SerializedName("source_transfer")
                var sourceTransfer: Any, // null
                @SerializedName("statement_descriptor")
                var statementDescriptor: Any, // null
                @SerializedName("statement_descriptor_suffix")
                var statementDescriptorSuffix: Any, // null
                @SerializedName("status")
                var status: String, // succeeded
                @SerializedName("transfer_data")
                var transferData: Any, // null
                @SerializedName("transfer_group")
                var transferGroup: Any // null
            ) {
                data class BillingDetails(
                    @SerializedName("address")
                    var address: Address,
                    @SerializedName("email")
                    var email: Any, // null
                    @SerializedName("name")
                    var name: Any, // null
                    @SerializedName("phone")
                    var phone: Any // null
                ) {
                    data class Address(
                        @SerializedName("city")
                        var city: Any, // null
                        @SerializedName("country")
                        var country: Any, // null
                        @SerializedName("line1")
                        var line1: Any, // null
                        @SerializedName("line2")
                        var line2: Any, // null
                        @SerializedName("postal_code")
                        var postalCode: Any, // null
                        @SerializedName("state")
                        var state: Any // null
                    )
                }

                class FraudDetails

                class Metadata

                data class Outcome(
                    @SerializedName("network_status")
                    var networkStatus: String, // approved_by_network
                    @SerializedName("reason")
                    var reason: Any, // null
                    @SerializedName("risk_level")
                    var riskLevel: String, // normal
                    @SerializedName("risk_score")
                    var riskScore: Int, // 23
                    @SerializedName("seller_message")
                    var sellerMessage: String, // Payment complete.
                    @SerializedName("type")
                    var type: String // authorized
                )

                data class PaymentMethodDetails(
                    @SerializedName("card")
                    var card: Card,
                    @SerializedName("type")
                    var type: String // card
                ) {
                    data class Card(
                        @SerializedName("brand")
                        var brand: String, // visa
                        @SerializedName("checks")
                        var checks: Checks,
                        @SerializedName("country")
                        var country: String, // US
                        @SerializedName("exp_month")
                        var expMonth: Int, // 3
                        @SerializedName("exp_year")
                        var expYear: Int, // 2026
                        @SerializedName("fingerprint")
                        var fingerprint: String, // IjB2AFICTD7yNDEN
                        @SerializedName("funding")
                        var funding: String, // debit
                        @SerializedName("installments")
                        var installments: Any, // null
                        @SerializedName("last4")
                        var last4: String, // 5556
                        @SerializedName("mandate")
                        var mandate: Any, // null
                        @SerializedName("network")
                        var network: String, // visa
                        @SerializedName("three_d_secure")
                        var threeDSecure: Any, // null
                        @SerializedName("wallet")
                        var wallet: Any // null
                    ) {
                        data class Checks(
                            @SerializedName("address_line1_check")
                            var addressLine1Check: Any, // null
                            @SerializedName("address_postal_code_check")
                            var addressPostalCodeCheck: Any, // null
                            @SerializedName("cvc_check")
                            var cvcCheck: String // pass
                        )
                    }
                }

                data class Refunds(
                    @SerializedName("data")
                    var `data`: List<Any>,
                    @SerializedName("has_more")
                    var hasMore: Boolean, // false
                    @SerializedName("object")
                    var objectX: String, // list
                    @SerializedName("total_count")
                    var totalCount: Int, // 0
                    @SerializedName("url")
                    var url: String // /v1/charges/ch_1Kitx9IZXks92CWPaqDCf5nW/refunds
                )

                data class Source(
                    @SerializedName("address_city")
                    var addressCity: Any, // null
                    @SerializedName("address_country")
                    var addressCountry: Any, // null
                    @SerializedName("address_line1")
                    var addressLine1: Any, // null
                    @SerializedName("address_line1_check")
                    var addressLine1Check: Any, // null
                    @SerializedName("address_line2")
                    var addressLine2: Any, // null
                    @SerializedName("address_state")
                    var addressState: Any, // null
                    @SerializedName("address_zip")
                    var addressZip: Any, // null
                    @SerializedName("address_zip_check")
                    var addressZipCheck: Any, // null
                    @SerializedName("brand")
                    var brand: String, // Visa
                    @SerializedName("country")
                    var country: String, // US
                    @SerializedName("customer")
                    var customer: Any, // null
                    @SerializedName("cvc_check")
                    var cvcCheck: String, // pass
                    @SerializedName("dynamic_last4")
                    var dynamicLast4: Any, // null
                    @SerializedName("exp_month")
                    var expMonth: Int, // 3
                    @SerializedName("exp_year")
                    var expYear: Int, // 2026
                    @SerializedName("fingerprint")
                    var fingerprint: String, // IjB2AFICTD7yNDEN
                    @SerializedName("funding")
                    var funding: String, // debit
                    @SerializedName("id")
                    var id: String, // card_1Kitx8IZXks92CWP7XyZ6uSU
                    @SerializedName("last4")
                    var last4: String, // 5556
                    @SerializedName("metadata")
                    var metadata: Metadata,
                    @SerializedName("name")
                    var name: Any, // null
                    @SerializedName("object")
                    var objectX: String, // card
                    @SerializedName("tokenization_method")
                    var tokenizationMethod: Any // null
                ) {
                    class Metadata
                }
            }

            data class UserAddress(
                @SerializedName("address")
                var address: String, // Phase8
                @SerializedName("city")
                var city: String, // Mohali
                @SerializedName("createdAt")
                var createdAt: String, // 2022-03-23T06:43:57.000Z
                @SerializedName("id")
                var id: Int, // 31
                @SerializedName("latitude")
                var latitude: Double, // 31
                @SerializedName("longitude")
                var longitude: Double, // 77
                @SerializedName("name")
                var name: String, // user
                @SerializedName("state")
                var state: String, // Punjab
                @SerializedName("updatedAt")
                var updatedAt: String, // 2022-03-23T06:43:57.000Z
                @SerializedName("userId")
                var userId: Int, // 335
                @SerializedName("zipcode")
                var zipcode: String // 143001
            )
        }
    }
}