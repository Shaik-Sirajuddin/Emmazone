package com.live.emmazone.response_model


import com.google.gson.annotations.SerializedName

data class PrivacyPolicyResponse(
    @SerializedName("body")
    var body: Body,
    @SerializedName("code")
    var code: Int, // 200
    @SerializedName("message")
    var message: String, // Privacy Policy
    @SerializedName("success")
    var success: Boolean // true
) {
    data class Body(
        @SerializedName("accessor")
        var accessor: String, // privacyPolicy
        @SerializedName("content")
        var content: String, // <h3><span style="background-color: rgb(0, 0, 0);"><font color="#efefef"><span style="font-family: &quot;Open Sans&quot;, Arial, sans-serif; font-size: 14px; text-align: justify;">has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lo</span></font></span><br></h3>
        @SerializedName("created")
        var created: Int, // 1591716283
        @SerializedName("createdAt")
        var createdAt: String, // 2020-03-23T15:33:04.000Z
        @SerializedName("id")
        var id: Int, // 3
        @SerializedName("title")
        var title: String, // Privacy Policy
        @SerializedName("updated")
        var updated: Int, // 1637227772
        @SerializedName("updatedAt")
        var updatedAt: String // 2021-11-18T09:29:32.000Z
    )
}