package com.live.emmazone.response_model


import com.google.gson.annotations.SerializedName

data class CategoryColorSizeResponse(
    @SerializedName("body")
    var body: Body,
    @SerializedName("code")
    var code: Int, // 200
    @SerializedName("message")
    var message: String, //  Category color and size List
    @SerializedName("success")
    var success: Boolean // true
) {
    data class Body(
            @SerializedName("category_colors")
        var categoryColors: List<CategoryColor>,
        @SerializedName("category_sizes")
        var categorySizes: List<CategorySize>,
        @SerializedName("created")
        var created: Int, // 1644411182
        @SerializedName("createdAt")
        var createdAt: String, // 2022-02-09T12:53:02.000Z
        @SerializedName("hierarchyLevel")
        var hierarchyLevel: Int, // 1
        @SerializedName("id")
        var id: Int, // 24
        @SerializedName("image")
        var image: String, // 52d6a248-ea60-47d0-b459-14b0329b6144.jpg
        @SerializedName("name")
        var name: String, // TV
        @SerializedName("parentId")
        var parentId: Any, // null
        @SerializedName("status")
        var status: Int, // 1
        @SerializedName("updated")
        var updated: Int, // 1644411246
        @SerializedName("updatedAt")
        var updatedAt: String // 2022-02-09T12:54:06.000Z
    ) {
        data class CategoryColor(
            @SerializedName("categoryId")
            var categoryId: Int, // 24
            @SerializedName("color")
            var color: String, // Black
            @SerializedName("id")
            var id: Int, // 20
            var isSelected: Boolean = false
        )

        data class CategorySize(
            @SerializedName("categoryId")
            var categoryId: Int, // 24
            @SerializedName("id")
            var id: Int, // 26
            @SerializedName("size")
            var size: String, // 23 inch
            var isSelected: Boolean = false
        )
    }
}