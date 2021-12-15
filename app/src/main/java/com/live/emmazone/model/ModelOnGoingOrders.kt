package com.live.emmazone.model

import java.io.Serializable

data class ModelOnGoingOrders(
    val onGoingItem: Int,
    val onGoingItemName: String, val onGoingItemQuantity: String,
    val productPrice: String
) : Serializable
