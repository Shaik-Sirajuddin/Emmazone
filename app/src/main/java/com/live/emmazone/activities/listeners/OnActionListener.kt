package com.live.emmazone.activities.listeners

interface OnActionListener<T> {

    fun notify(model: T, position: Int)
}