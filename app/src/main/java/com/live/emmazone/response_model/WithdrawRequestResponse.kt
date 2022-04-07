package com.live.emmazone.response_model
import com.google.gson.annotations.SerializedName


data class WithdrawRequestResponse(
    @SerializedName("body")
    var body: Body,
    @SerializedName("code")
    var code: Int, // 200
    @SerializedName("message")
    var message: String, // User profile fetched successfully.
    @SerializedName("success")
    var success: Boolean // true
) {
    data class Body(
        @SerializedName("requestAmount")
        var requestAmount: RequestAmount,
        @SerializedName("updatedBalance")
        var updatedBalance: UpdatedBalance,
        @SerializedName("User")
        var user: User
    ) {
        data class RequestAmount(
            @SerializedName("amount")
            var amount: String, // 50
            @SerializedName("bankId")
            var bankId: String, // 3
            @SerializedName("created")
            var created: Int, // 1649311537
            @SerializedName("createdAt")
            var createdAt: CreatedAt,
            @SerializedName("id")
            var id: Int, // 53
            @SerializedName("message")
            var message: String, // Withdraw Request
            @SerializedName("status")
            var status: Int, // 0
            @SerializedName("updated")
            var updated: Int, // 1649311537
            @SerializedName("updatedAt")
            var updatedAt: UpdatedAt,
            @SerializedName("vendorId")
            var vendorId: Int // 336
        ) {
            data class CreatedAt(
                @SerializedName("val")
                var valX: String // CURRENT_TIMESTAMP
            )

            data class UpdatedAt(
                @SerializedName("val")
                var valX: String // CURRENT_TIMESTAMP
            )
        }

        data class UpdatedBalance(
            @SerializedName("created")
            var created: Int, // 1647928526
            @SerializedName("id")
            var id: Int, // 336
            @SerializedName("role")
            var role: Int, // 3
            @SerializedName("updated")
            var updated: Int, // 1647928526
            @SerializedName("username")
            var username: String, // Seller
            @SerializedName("walletBalance")
            var walletBalance: String // 576.45
        )

        data class User(
            @SerializedName("id")
            var id: Int, // 336
            @SerializedName("walletBalance")
            var walletBalance: String // 576.45
        )
    }
}