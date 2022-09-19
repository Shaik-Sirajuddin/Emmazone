package com.live.emmazone.utils

import android.content.Context
import androidx.multidex.MultiDexApplication

class App : MultiDexApplication() {
    var appContext: Context? = null

    companion object {

        lateinit var application: App

        fun getInstance(): App {
            return application
        }
    }

    override fun onCreate() {
        super.onCreate()
        application = this
        appContext = applicationContext
    }
}
