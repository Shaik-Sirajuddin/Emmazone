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
        var totalEarning: Double, // 576.45
        @SerializedName("total_product")
        var totalProduct: Int // 1
    ) {
        data class FindOrder(
            @SerializedName("adminCommission")
            var adminCommission: String, // 64.05
            @SerializedName("created")
            var created: Int, // 1649316897
            @SerializedName("createdAt")
            var createdAt: String, // 2022-04-07T07:34:56.000Z
            @SerializedName("customerId")
            var customerId: Int, // 335
            @SerializedName("deliveryType")
            var deliveryType: Int, // 2
            @SerializedName("id")
            var id: Int, // 2
            @SerializedName("netAmount")
            var netAmount: String, // 600.00
            @SerializedName("orderJson")
            var orderJson: String, // {"paymentMethod":"1","payment":{"id":"ch_1KlprnIZXks92CWPTRKu2g7o","object":"charge","amount":64050,"amount_captured":64050,"amount_refunded":0,"application":null,"application_fee":null,"application_fee_amount":null,"balance_transaction":"txn_1KlproIZXks92CWPjezF33dY","billing_details":{"address":{"city":null,"country":null,"line1":null,"line2":null,"postal_code":null,"state":null},"email":null,"name":null,"phone":null},"calculated_statement_descriptor":"Stripe","captured":true,"created":1649316895,"currency":"inr","customer":null,"description":"Emmazone","destination":null,"dispute":null,"disputed":false,"failure_balance_transaction":null,"failure_code":null,"failure_message":null,"fraud_details":{},"invoice":null,"livemode":false,"metadata":{},"on_behalf_of":null,"order":null,"outcome":{"network_status":"approved_by_network","reason":null,"risk_level":"normal","risk_score":57,"seller_message":"Payment complete.","type":"authorized"},"paid":true,"payment_intent":null,"payment_method":"card_1KlprmIZXks92CWPCe2aAGSn","payment_method_details":{"card":{"brand":"visa","checks":{"address_line1_check":null,"address_postal_code_check":null,"cvc_check":"pass"},"country":"US","exp_month":3,"exp_year":2026,"fingerprint":"IjB2AFICTD7yNDEN","funding":"debit","installments":null,"last4":"5556","mandate":null,"network":"visa","three_d_secure":null,"wallet":null},"type":"card"},"receipt_email":null,"receipt_number":null,"receipt_url":"https://pay.stripe.com/receipts/acct_1DT5HDIZXks92CWP/ch_1KlprnIZXks92CWPTRKu2g7o/rcpt_LSlO2rFmt1SmF4ThvCg3WDEs0aOsSLq","refunded":false,"refunds":{"object":"list","data":[],"has_more":false,"total_count":0,"url":"/v1/charges/ch_1KlprnIZXks92CWPTRKu2g7o/refunds"},"review":null,"shipping":null,"source":{"id":"card_1KlprmIZXks92CWPCe2aAGSn","object":"card","address_city":null,"address_country":null,"address_line1":null,"address_line1_check":null,"address_line2":null,"address_state":null,"address_zip":null,"address_zip_check":null,"brand":"Visa","country":"US","customer":null,"cvc_check":"pass","dynamic_last4":null,"exp_month":3,"exp_year":2026,"fingerprint":"IjB2AFICTD7yNDEN","funding":"debit","last4":"5556","metadata":{},"name":null,"tokenization_method":null},"source_transfer":null,"statement_descriptor":null,"statement_descriptor_suffix":null,"status":"succeeded","transfer_data":null,"transfer_group":null},"userAddress":{"id":31,"userId":335,"name":"user","address":"Phase8","city":"Mohali","state":"Punjab","latitude":31,"longitude":77,"zipcode":"143001","createdAt":"2022-03-23T06:43:57.000Z","updatedAt":"2022-03-23T06:43:57.000Z"},"orderItems":[{"id":150,"userId":336,"status":1,"name":"Shoes","product_quantity":10,"product_highlight":1,"product_price":"600.00","categoryId":34,"categoryColorId":80,"categorySizeId":77,"description":"Good quality, Light weight","productReview":"0.0","mainImage":"http://202.164.42.227:8188/uploads/product/db8ee817-18f0-454b-b74b-ce399f13ceb4.jpg","created":1647928878,"createdAt":"2022-03-22T06:01:18.000Z","vendor":{"id":336,"username":"Seller","image":"http://202.164.42.227:8188/uploads/user/2022-04-07 13:12:49.896 18597-20537/com.live.emmazone I/okhttp.OkHttpClient: f63d7ec6-9393-4a16-ab71-492e1ddd60fe.jpg"},"vendorId":336,"orderedQty":1,"orderedQtyPrice":600}]}
            @SerializedName("orderNo")
            var orderNo: String, // 1649-316896-5561
            @SerializedName("orderStatus")
            var orderStatus: Int, // 0
            @SerializedName("paymentMethod")
            var paymentMethod: Int, // 1
            @SerializedName("productName")
            var productName: String,
            @SerializedName("qrCode")
            var qrCode: String, // https://api.qrserver.com/v1/create-qr-code/?size=100x100&data=2
            @SerializedName("qty")
            var qty: Int, // 0
            @SerializedName("sellerCommission")
            var sellerCommission: String, // 576.45
            @SerializedName("shippingCharges")
            var shippingCharges: String, // 10.00
            @SerializedName("taxCharged")
            var taxCharged: String, // 30.50
            @SerializedName("total")
            var total: String, // 640.50
            @SerializedName("updated")
            var updated: Int, // 1649316897
            @SerializedName("updatedAt")
            var updatedAt: String, // 2022-04-07T07:34:56.000Z
            @SerializedName("userAddressId")
            var userAddressId: Int, // 31
            @SerializedName("vendorId")
            var vendorId: Int // 336
        )
    }
}