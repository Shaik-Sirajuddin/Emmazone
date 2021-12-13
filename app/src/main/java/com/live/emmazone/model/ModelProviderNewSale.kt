package com.live.emmazone.model

import java.io.Serializable

data class ModelProviderNewSale(
    val tvOrder: String, val tvOrderID: String, val imageSales: Int, val tvUsername: String,
    val tvDeliveryType: String, val tvHomeDelivery: String, val list: ArrayList<ModelOnGoingOrders>,
    val imgStatus: Int, val tvODOrderDate: String, var status: String
) : Serializable
