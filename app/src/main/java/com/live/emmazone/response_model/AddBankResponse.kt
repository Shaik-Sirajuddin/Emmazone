package com.live.emmazone.response_model


import com.google.gson.annotations.SerializedName

data class AddBankResponse(
    @SerializedName("body")
    var body: Body,
    @SerializedName("code")
    var code: Int, // 200
    @SerializedName("message")
    var message: String, // Bank details added successfully
    @SerializedName("success")
    var success: Boolean // true
) {
    data class Body(
        @SerializedName("accountHolderName")
        var accountHolderName: String, // Abc
        @SerializedName("accountNumber")
        var accountNumber: String, // 998877665544332211
        @SerializedName("bankBranch")
        var bankBranch: String, // SBI
        @SerializedName("created")
        var created: Int, // 1648202398
        @SerializedName("createdAt")
        var createdAt: String, // 2022-03-25T09:59:57.000Z
        @SerializedName("id")
        var id: Int, // 1
        @SerializedName("ifscSwiftCode")
        var ifscSwiftCode: String, // Sbi0001
        @SerializedName("updated")
        var updated: Int, // 1648202398
        @SerializedName("updatedAt")
        var updatedAt: String, // 2022-03-25T09:59:57.000Z
        @SerializedName("userId")
        var userId: Int // 336
    )
}