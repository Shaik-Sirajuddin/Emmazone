package com.live.emmazone.model

import android.widget.RadioButton

data class ModelDeliveryAddress(
    val tvOrderPersonName: String,
    val tvOrderDeliveryAddress: String,
    var isSelected: Boolean = false
)
