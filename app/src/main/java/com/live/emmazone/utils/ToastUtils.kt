package com.live.emmazone.utils

import android.content.Context
import android.widget.Toast

object ToastUtils {

    fun showToast(msg: Int) {
        Toast.makeText(App.getInstance().appContext, msg, Toast.LENGTH_SHORT).show()
    }

    fun showToast(msg: String) {
        Toast.makeText(App.getInstance().appContext, msg, Toast.LENGTH_SHORT).show()
    }

    fun showLongToast(msg: Int) {
        Toast.makeText(App.getInstance().appContext, msg, Toast.LENGTH_LONG).show()
    }

    fun showLongToast(msg: String) {
        Toast.makeText(App.getInstance().appContext, msg, Toast.LENGTH_LONG).show()
    }
}