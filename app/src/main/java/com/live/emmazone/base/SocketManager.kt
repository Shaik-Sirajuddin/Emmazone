package com.live.emmazone.base

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.live.emmazone.extensionfuncton.getPreference
import com.live.emmazone.utils.App
import com.live.emmazone.utils.AppConstants
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import io.socket.engineio.client.transports.WebSocket
import org.json.JSONObject
import java.net.URISyntaxException

object SocketManager {

    private var mSocket: Socket? = null
    private var observerList: MutableList<SocketInterface>? = null
    private var isConnect = false
    private val TAG = SocketManager::class.java.canonicalName


    //**************************EVENT*************************
    private const val CONNECT_USER = "connect_user"  //event
    const val SEND_MSG = "send_message"
    const val GET_MSG = "get_message"
    const val CHAT_LISTING = "chat_listing"
    const val READ_UNREAD = "read_unread"


    //**************************LISTENER*************************
    private const val CONNECT_LISTENER = "connect_listener"  //listener
    const val NEW_MSG_LISTENER = "new_message"
    const val GET_DATA_MSG_LISTENER = "get_data_message"
    const val CHAT_MSG_LISTENER = "chat_message"
    const val READ_STATUS_LISTENER = "read_status"


    init {
        try {
            createSocketOptions()
        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }
    }

    fun initSocket() {
        try {
            createSocketOptions()
            onConnect()
        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }
    }

    fun isConnected(): Boolean {
        return isConnect
    }

    private fun createSocketOptions() {
        val opts = IO.Options()
        //opts.transports  = arrayOf(WebSocket.NAME)
        opts.reconnection = true
        Log.e("socket", AppConstants.SOCKET_BASE_URL)
        mSocket = IO.socket("http://192.168.74.78:8101", opts)
        if (observerList == null || observerList!!.size == 0) {
            observerList = ArrayList()
        }
    }

    private fun onConnect() {
        if (mSocket == null)
            return
        if (!mSocket!!.connected()) {
            mSocket!!.on(Socket.EVENT_CONNECT, onConnectListener)
            mSocket!!.on(Socket.EVENT_DISCONNECT, onDisConnect)
            mSocket!!.on(Socket.EVENT_CONNECT_ERROR, onConnectError)
//            mSocket!!.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError)
            mSocket!!.on(CONNECT_LISTENER, onConnectUserListener)
            mSocket!!.on(NEW_MSG_LISTENER, onNewMsgListener)
            mSocket!!.on(GET_DATA_MSG_LISTENER, onDataMsgListener)
            mSocket!!.on(CHAT_MSG_LISTENER, onMsgListListener)
            mSocket!!.on(READ_STATUS_LISTENER, readUnreadListener)


            mSocket!!.connect()
        } else {
            Log.e("info", "Socket connected: ${mSocket?.id()}")
        }
    }

    /**
     * Call this method in onPause and onDestroy
     */
    fun onDisConnect() {
        Log.e("onDisConnect", "Socket disConnected: ${mSocket?.id()}")
        if (mSocket == null)
            return
        isConnect = false
        mSocket!!.off(Socket.EVENT_CONNECT, onConnectListener)
        mSocket!!.off(Socket.EVENT_DISCONNECT, onDisConnect)
        mSocket!!.off(Socket.EVENT_CONNECT_ERROR, onConnectError)
        mSocket!!.off(CONNECT_LISTENER, onConnectUserListener)
        mSocket!!.off(NEW_MSG_LISTENER, onNewMsgListener)
        mSocket!!.off(GET_DATA_MSG_LISTENER, onDataMsgListener)
        mSocket!!.off(CHAT_MSG_LISTENER, onMsgListListener)
        mSocket!!.off(READ_STATUS_LISTENER, readUnreadListener)
        mSocket!!.disconnect()
    }


    /*
     * Send Data to server by use of socket
     * */
    fun sendDataToServer(methodName: String?, mObject: Any) {
        // Get a handler that can be used to post to the main thread
        Handler(Looper.getMainLooper()).post {
            mSocket?.emit(methodName, mObject)
            Log.e(methodName, mObject.toString())
        }
    }


    private val onConnectUserListener = Emitter.Listener { args ->
        // Get a handler that can be used to post to the main thread
        Handler(Looper.getMainLooper()).post { //                    JSONObject data = (JSONObject) args[0];
            Log.e("onConnectUserListener", args[0].toString())
            for (observer in observerList!!) {
                observer.onSocketCall(CONNECT_LISTENER, *args)
            }
        }
    }

    /********************************************************************************/
    private val onConnectListener = Emitter.Listener { args ->
        // Get a handler that can be used to post to the main thread
        Log.e("onConnectListener", "Socket connected: ${mSocket?.id()}")
        Handler(Looper.getMainLooper()).post {
            Log.e(TAG, "socket connected")
            isConnect = true

            try {

                val userId = getPreference(AppConstants.USER_ID, "")

                if (userId.isNotEmpty()) {
                    val jsonObject = JSONObject()
                    jsonObject.put("userId", userId)
                    sendDataToServer(CONNECT_USER, jsonObject)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            for (observer in observerList!!) {
                observer.onSocketConnect(*args)
            }
        }
    }

    private val onDisConnect = Emitter.Listener { args ->
        // Get a handler that can be used to post to the main thread
        Handler(Looper.getMainLooper()).post {
            Log.e(TAG, "socket disConnected")
            isConnect = false
            //                    if (mSocketInterface!=null)
//                        mSocketInterface.onSocketDisConnect(args);
            for (observer in observerList!!) {
                observer.onSocketDisConnect(*args)
            }
        }
    }

    private val onConnectError =
        Emitter.Listener { args -> // Get a handler that can be used to post to the main thread
            Handler(Looper.getMainLooper()).post {
                for (observer in observerList!!) {
                    observer.onError("ERROR", *args)
                }
                Log.e(TAG, "Run" + args[0])
                Log.e(TAG, "socket Error connecting")
//                if (!checkStringNull(MyApplication.instance!!.getString(Constants.AuthKey)))
//                    initSocket()
            }
        }


    fun onRegister(observer: SocketInterface) {
        if (observerList != null && !observerList!!.contains(observer)) {
            observerList!!.add(observer)
        } else {
            observerList = ArrayList()
            observerList!!.add(observer)
        }
    }

    fun unRegister(observer: SocketInterface) {
        if (!observerList.isNullOrEmpty()) {
            for (i in observerList!!.indices) {
                /*if (!checkObjectNull(observerList!![i])) {*/
                if (i < observerList!!.size) {
                    val model = observerList!![i]
                    if (model === observer) {
                        observerList!!.remove(model)
                    }
                }
                /*  }*/
            }
        }
    }


    private val onDataMsgListener = Emitter.Listener { args ->
        Handler(Looper.getMainLooper()).post {
            for (observer in observerList!!) {
                observer.onSocketCall(GET_DATA_MSG_LISTENER, args)
            }
        }
    }

    private val onNewMsgListener = Emitter.Listener { args ->
        Handler(Looper.getMainLooper()).post {
            for (observer in observerList!!) {
                observer.onSocketCall(NEW_MSG_LISTENER, args)
            }
        }
    }

    private val onMsgListListener = Emitter.Listener { args ->
        Handler(Looper.getMainLooper()).post {
            for (observer in observerList!!) {
                observer.onSocketCall(CHAT_MSG_LISTENER, args)
            }
        }
    }
    private val readUnreadListener = Emitter.Listener { args ->
        Handler(Looper.getMainLooper()).post {
            for (observer in observerList!!) {
                observer.onSocketCall(READ_STATUS_LISTENER, args)
            }
        }
    }

    interface SocketInterface {
        fun onSocketCall(event: String?, vararg args: Any?)
        fun onSocketConnect(vararg args: Any?)
        fun onSocketDisConnect(vararg args: Any?)
        fun onError(event: String?, vararg args: Any?)
    }


}