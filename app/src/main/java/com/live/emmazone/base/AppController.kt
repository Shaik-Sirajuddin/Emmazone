package com.live.emmazone.base

import androidx.multidex.MultiDexApplication
import com.live.emmazone.utils.MediaLoader
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumConfig
import java.util.*

class AppController : MultiDexApplication(), AppLifecycleHandler.AppLifecycleDelegates {

    private var lifecycleHandler: AppLifecycleHandler? = null

    companion object {
        @get:Synchronized
        var instance: AppController? = null
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
        lifecycleHandler = AppLifecycleHandler(this)
        registerLifecycleHandler(lifecycleHandler!!)

        Album.initialize(
            AlbumConfig.newBuilder(this)
                .setAlbumLoader(MediaLoader())
                .setLocale(Locale.getDefault())
                .build()
        )
    }

    private fun registerLifecycleHandler(lifecycleHandler: AppLifecycleHandler) {
        registerActivityLifecycleCallbacks(lifecycleHandler)
        registerComponentCallbacks(lifecycleHandler)
    }

    override fun onAppForegrounded() {
//        if (!SocketManager.isConnected()) {
//            SocketManager.initSocket()
//        }
    }

    override fun onAppBackgrounded() {
//        SocketManager.onDisConnect()
    }
}
