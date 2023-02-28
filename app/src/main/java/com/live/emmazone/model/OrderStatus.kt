package com.live.emmazone.model

class OrderStatus {
    companion object {
        const val PENDING = 0
        const val ON_THE_WAY = 1
        const val DELIVERED = 2
        const val CANCELLED = 3
        const val RETURN_IN_TRANSIT = 7
        const val RETURNED = 8
        const val CANCEL_RETURN = 9
    }
}