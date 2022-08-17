package com.live.emmazone.base

import android.app.Activity
import android.app.Application
import android.content.ComponentCallbacks2
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log

class AppLifecycleHandler : Application.ActivityLifecycleCallbacks, ComponentCallbacks2 {
    private var lifecycleDelegates: AppLifecycleDelegates? = null
    private var appInForeground = false

    constructor(delegates: AppLifecycleDelegates?) {
        lifecycleDelegates = delegates
    }


    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        Log.i("Application", "Created")
    }

    override fun onActivityStarted(activity: Activity) {
        Log.i("Application", "Started")
    }

    override fun onActivityResumed(activity: Activity) {
        Log.i("Application", "Resumed")
        if (!appInForeground) {
            appInForeground = true
            lifecycleDelegates!!.onAppForegrounded()
        }
    }

    override fun onActivityPaused(activity: Activity) {
        Log.i("Application", "Paused")
    }

    override fun onActivityStopped(activity: Activity) {
        Log.i("Application", "Stopped")
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    override fun onActivityDestroyed(activity: Activity) {
        Log.i("Application", "Destroyed")
    }

    override fun onConfigurationChanged(newConfig: Configuration) {

    }

    override fun onLowMemory() {

    }

    override fun onTrimMemory(level: Int) {
        Log.i("Application", "onTrimMemory")
        if (level == ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN) {
            appInForeground = false
            // lifecycleDelegate instance was passed in on the constructor
            lifecycleDelegates!!.onAppBackgrounded()
        }
    }

    interface AppLifecycleDelegates {
        fun onAppForegrounded()
        fun onAppBackgrounded()
    }
}