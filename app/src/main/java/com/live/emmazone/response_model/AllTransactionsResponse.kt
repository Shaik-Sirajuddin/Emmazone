package com.live.emmazone.response_model
import com.google.gson.annotations.SerializedName


data class AllTransactionsResponse(
    @SerializedName("body")
    var body: Body,
    @SerializedName("code")
    var code: Int, // 200
    @SerializedName("message")
    var message: String, // Transaction list fetched successfully.
    @SerializedName("success")
    var success: Boolean // true
) {
    data class Body(
        @SerializedName("Balance")
        var balance: Balance,
        @SerializedName("withdrawlist")
        var withdrawlist: ArrayList<Withdrawlist>
    ) {
        data class Balance(
            @SerializedName("walletBalance")
            var walletBalance: String // 576.45
        )

        data class Withdrawlist(
            @SerializedName("amount")
            var amount: Int, // 100
            @SerializedName("bank")
            var bank: Bank?,
            @SerializedName("bankId")
            var bankId: Int, // 3
            @SerializedName("created")
            var created: Int, // 1649312345
            @SerializedName("createdAt")
            var createdAt: String, // 2022-04-07T06:19:04.000Z
            @SerializedName("id")
            var id: Int, // 54
            @SerializedName("message")
            var message: String, // Withdraw Request
            @SerializedName("status")
            var status: Int, // 0
            @SerializedName("updated")
            var updated: Int, // 1649312345
            @SerializedName("updatedAt")
            var updatedAt: String, // 2022-04-07T06:19:04.000Z
            @SerializedName("vendorId")
            var vendorId: Int // 336
        ) {
            data class Bank(
                @SerializedName("accountHolderName")
                var accountHolderName: String?, // Qwerty
                @SerializedName("accountNumber")
                var accountNumber: String?, // 147852369
                @SerializedName("bankBranch")
                var bankBranch: String?, // HDFC
                @SerializedName("id")
                var id: Int? // 3
            )
        }
    }
}