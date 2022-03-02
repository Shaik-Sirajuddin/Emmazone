package com.live.emmazone.response_model

data class FaqListResponse(
    val body: ArrayList<Body>,
    val code: Int,
    val message: String,
    val success: Boolean
) {
    data class Body(
        val answer: String,
        val created: Int,
        val createdAt: String,
        val id: Int,
        val status: Int,
        val title: String,
        val updated: Int,
        val updatedAt: String,
        var isSelected: Boolean=false
    )
}