package com.live.emmazone.response_model

import java.io.Serializable

data class SalesResponse(
    val body: ArrayList<SaleResponseBody>,
    val code: Int,
    val message: String,
    val success: Boolean
):Serializable {
    data class SaleResponseBody(
        val adminCommission: String,
        val created: Long,
        val createdAt: String,
        val customer: Customer,
        val customerId: Int,
        val deliveryType: Int,
        val id: Int,
        val netAmount: String,
        val orderJson: OrderJson,
        val orderNo: String,
        val orderStatus: Int,
        val paymentMethod: Int,
        val productName: String,
        val shippingCharges: String,
        val taxCharged: String,
        val total: String,
        val updated: Int,
        val updatedAt: String,
        val userAddressId: Int,
        val vendor: Vendor,
        val vendorId: Int
    ):Serializable {
        data class Customer(
            val id: Int,
            val image: String,
            val username: String
        ):Serializable

        data class OrderJson(
            val orderItems: ArrayList<OrderItem>,
            val payment: Payment,
            val paymentMethod: String,
            val userAddress: UserAddress
        ):Serializable {
            data class OrderItem(
                val categoryColorId: Int,
                val categoryId: Int,
                val categorySizeId: Int,
                val created: Int,
                val createdAt: String,
                val description: String,
                val id: Int,
                val mainImage: String,
                val name: String,
                val orderedQty: Int,
                val orderedQtyPrice: Int,
                val productReview: String,
                val product_highlight: Int,
                val product_price: String,
                val product_quantity: Int,
                val status: Int,
                val userId: Int,
                val vendor: Vendor
            ) :Serializable{
                data class Vendor(
                    val id: Int,
                    val image: String,
                    val username: String
                ):Serializable
            }

            data class Payment(
                val amount: Int,
                val amount_captured: Int,
                val amount_refunded: Int,
                val application: Any,
                val application_fee: Any,
                val application_fee_amount: Any,
                val balance_transaction: String,
                val billing_details: BillingDetails,
                val calculated_statement_descriptor: String,
                val captured: Boolean,
                val created: Int,
                val currency: String,
                val customer: Any,
                val description: String,
                val destination: Any,
                val dispute: Any,
                val disputed: Boolean,
                val failure_code: Any,
                val failure_message: Any,
                val id: String,
                val invoice: Any,
                val livemode: Boolean,
                val on_behalf_of: Any,
                val order: Any,
                val outcome: Outcome,
                val paid: Boolean,
                val payment_intent: Any,
                val payment_method: String,
                val payment_method_details: PaymentMethodDetails,
                val receipt_email: Any,
                val receipt_number: Any,
                val receipt_url: String,
                val refunded: Boolean,
                val refunds: Refunds,
                val review: Any,
                val shipping: Any,
                val source: Source,
                val source_transfer: Any,
                val statement_descriptor: Any,
                val statement_descriptor_suffix: Any,
                val status: String,
                val transfer_data: Any,
                val transfer_group: Any
            ) :Serializable{
                data class BillingDetails(
                    val address: Address,
                    val email: Any,
                    val name: Any,
                    val phone: Any
                ):Serializable {
                    data class Address(
                        val city: Any,
                        val country: Any,
                        val line1: Any,
                        val line2: Any,
                        val postal_code: Any,
                        val state: Any
                    ):Serializable
                }

                data class Outcome(
                    val network_status: String,
                    val reason: Any,
                    val risk_level: String,
                    val risk_score: Int,
                    val seller_message: String,
                    val type: String
                ):Serializable

                data class PaymentMethodDetails(
                    val card: Card,
                    val type: String
                ):Serializable {
                    data class Card(
                        val brand: String,
                        val checks: Checks,
                        val country: String,
                        val exp_month: Int,
                        val exp_year: Int,
                        val fingerprint: String,
                        val funding: String,
                        val installments: Any,
                        val last4: String,
                        val network: String,
                        val three_d_secure: Any,
                        val wallet: Any
                    ):Serializable {
                        data class Checks(
                            val address_line1_check: Any,
                            val address_postal_code_check: Any,
                            val cvc_check: String
                        ):Serializable
                    }
                }

                data class Refunds(
                    val `data`: List<Any>,
                    val has_more: Boolean,
                    val total_count: Int,
                    val url: String
                ):Serializable

                data class Source(
                    val address_city: Any,
                    val address_country: Any,
                    val address_line1: Any,
                    val address_line1_check: Any,
                    val address_line2: Any,
                    val address_state: Any,
                    val address_zip: Any,
                    val address_zip_check: Any,
                    val brand: String,
                    val country: String,
                    val customer: Any,
                    val cvc_check: String,
                    val dynamic_last4: Any,
                    val exp_month: Int,
                    val exp_year: Int,
                    val fingerprint: String,
                    val funding: String,
                    val id: String,
                    val last4: String,
                    val name: Any,
                    val tokenization_method: Any
                ) :Serializable
            }

            data class UserAddress(
                val address: String,
                val city: String,
                val createdAt: String,
                val id: Int,
                val latitude: Int,
                val longitude: Int,
                val name: String,
                val state: String,
                val updatedAt: String,
                val userId: Int,
                val zipcode: String
            ):Serializable
        }

        data class Vendor(
            val id: Int,
            val image: String,
            val username: String
        ):Serializable
    }
}