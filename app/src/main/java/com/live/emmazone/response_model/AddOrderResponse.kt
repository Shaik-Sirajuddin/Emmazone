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
        var created: Int, // 1646313704
        @SerializedName("createdAt")
        var createdAt: String, // 2022-03-03T13:21:44.000Z
        @SerializedName("customer")
        var customer: Customer,
        @SerializedName("customerId")
        var customerId: Int, // 267
        @SerializedName("deliveryType")
        var deliveryType: Int, // 2
        @SerializedName("id")
        var id: Int, // 4
        @SerializedName("netAmount")
        var netAmount: String, // 173.00
        @SerializedName("orderJson")
        var orderJson: OrderJson,
        @SerializedName("orderNo")
        var orderNo: String, // 1646-313704-0661
        @SerializedName("orderStatus")
        var orderStatus: Int, // 0
        @SerializedName("paymentMethod")
        var paymentMethod: Int, // 1
        @SerializedName("productName")
        var productName: String,
        @SerializedName("shippingCharges")
        var shippingCharges: String, // 10.00
        @SerializedName("taxCharged")
        var taxCharged: String, // 9.15
        @SerializedName("total")
        var total: String, // 192.15
        @SerializedName("updated")
        var updated: Int, // 1646313704
        @SerializedName("updatedAt")
        var updatedAt: String, // 2022-03-03T13:21:44.000Z
        @SerializedName("userAddressId")
        var userAddressId: Int, // 19
        @SerializedName("vendorId")
        var vendorId: Int // 0
    ) {
        data class Customer(
            @SerializedName("email")
            var email: String, // user@gmail.com
            @SerializedName("id")
            var id: Int // 267
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
                var categoryColorId: Int, // 27
                @SerializedName("categoryId")
                var categoryId: Int, // 22
                @SerializedName("categorySizeId")
                var categorySizeId: Int, // 35
                @SerializedName("created")
                var created: Int, // 1645706977
                @SerializedName("createdAt")
                var createdAt: String, // 2022-02-24T12:49:36.000Z
                @SerializedName("description")
                var description: String, // helloo
                @SerializedName("id")
                var id: Int, // 113
                @SerializedName("mainImage")
                var mainImage: String, // http://202.164.42.227:8188/uploads/product/621d5597-77f6-4179-8978-4670e1217bf7.jpg
                @SerializedName("name")
                var name: String, // Product Name
                @SerializedName("orderedQty")
                var orderedQty: Int, // 1
                @SerializedName("orderedQtyPrice")
                var orderedQtyPrice: Int, // 11
                @SerializedName("product_highlight")
                var productHighlight: Int, // 1
                @SerializedName("product_price")
                var productPrice: String, // 11.00
                @SerializedName("product_quantity")
                var productQuantity: Int, // 67667
                @SerializedName("productReview")
                var productReview: String, // 0.0
                @SerializedName("status")
                var status: Int, // 0
                @SerializedName("userId")
                var userId: Int, // 299
                @SerializedName("vendor")
                var vendor: Vendor
            ) {
                data class Vendor(
                    @SerializedName("id")
                    var id: Int, // 299
                    @SerializedName("image")
                    var image: String, // http://202.164.42.227:8188/uploads/user/bf2ab579-f34d-4d5c-9469-fafb00b9e882.jpg
                    @SerializedName("username")
                    var username: String // new user
                )
            }

            data class Payment(
                @SerializedName("amount")
                var amount: Int, // 19215
                @SerializedName("amount_captured")
                var amountCaptured: Int, // 19215
                @SerializedName("amount_refunded")
                var amountRefunded: Int, // 0
                @SerializedName("application")
                var application: Any, // null
                @SerializedName("application_fee")
                var applicationFee: Any, // null
                @SerializedName("application_fee_amount")
                var applicationFeeAmount: Any, // null
                @SerializedName("balance_transaction")
                var balanceTransaction: String, // txn_1KZEbDIZXks92CWPxtG8fWTS
                @SerializedName("billing_details")
                var billingDetails: BillingDetails,
                @SerializedName("calculated_statement_descriptor")
                var calculatedStatementDescriptor: String, // Stripe
                @SerializedName("captured")
                var captured: Boolean, // true
                @SerializedName("created")
                var created: Int, // 1646313702
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
                @SerializedName("failure_code")
                var failureCode: Any, // null
                @SerializedName("failure_message")
                var failureMessage: Any, // null
                @SerializedName("fraud_details")
                var fraudDetails: FraudDetails,
                @SerializedName("id")
                var id: String, // ch_1KZEbCIZXks92CWP6vKR5XI7
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
                var paymentMethod: String, // card_1KZEbCIZXks92CWPjwpVPiJ9
                @SerializedName("payment_method_details")
                var paymentMethodDetails: PaymentMethodDetails,
                @SerializedName("receipt_email")
                var receiptEmail: Any, // null
                @SerializedName("receipt_number")
                var receiptNumber: Any, // null
                @SerializedName("receipt_url")
                var receiptUrl: String, // https://pay.stripe.com/receipts/acct_1DT5HDIZXks92CWP/ch_1KZEbCIZXks92CWP6vKR5XI7/rcpt_LFk5lyr7uezirKkpwB2GzBk2bWVJTUh
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
                    var riskScore: Int, // 40
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
                    var url: String // /v1/charges/ch_1KZEbCIZXks92CWP6vKR5XI7/refunds
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
                    var id: String, // card_1KZEbCIZXks92CWPjwpVPiJ9
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
            )
        }
    }
}