package com.live.emmazone.push

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.live.emmazone.MainActivity
import com.live.emmazone.R
import com.live.emmazone.activities.main.Notifications
import com.live.emmazone.activities.provider.ProviderMainActivity
import com.live.emmazone.extensionfuncton.getPreference
import com.live.emmazone.utils.AppConstants
import org.json.JSONObject
import java.util.*

class MyFirebaseMessagingService : FirebaseMessagingService() {
    lateinit var resultIntent: PendingIntent
    lateinit var notificationChannel: NotificationChannel
    lateinit var mNotificationManager: NotificationManager
    lateinit var CHANNEL_ID: String

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        Log.d("tokenAPP", "$p0///////")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        if (remoteMessage.data != null) {
            Log.d("messagingResponse", remoteMessage.data.toString() + "///////")
        } else {
            Log.d("messagingResponse", remoteMessage.notification.toString())
        }
        if (remoteMessage.data != null) {
//            val mObject = JSONObject(remoteMessage.data["title"]).toString()
            val title = JSONObject(remoteMessage.data["title"]).get("title").toString()
            val msg = JSONObject(remoteMessage.data["title"]).get("msg").toString()
            val type = remoteMessage.data["type"].toString()

//            val pushResponse = Gson().fromJson(mObject, PushResponse::class.java)

            if (type == "1") {
                //type 1 = order,,, 2 = Chat
                if (getPreference(AppConstants.ROLE, "") == AppConstants.USER_ROLE) {
                    val notificationIntent = Intent(this, Notifications::class.java)
                    notificationIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    createNotification(title, msg, notificationIntent)
                } else {
                    val notificationIntent = Intent(this, Notifications::class.java)
                    notificationIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    createNotification(title, msg, notificationIntent)
                }
            } else {
                if (AppConstants.SEND_CHAT_PUSH) {
                    if (getPreference(AppConstants.ROLE, "") == AppConstants.USER_ROLE) {
                        val notificationIntent = Intent(this, MainActivity::class.java)
                        notificationIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        createNotification(title, msg, notificationIntent)
                    } else {
                        val notificationIntent = Intent(this, ProviderMainActivity::class.java)
                        notificationIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        createNotification(title, msg, notificationIntent)
                    }
                }

            }

        }
    }

    private fun createNotification(title: String, message: String, intent: Intent) {
        mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        //        if (intent == null) {
        val sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)


        CHANNEL_ID = applicationContext.packageName
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importanceLow = NotificationManager.IMPORTANCE_HIGH
            notificationChannel = NotificationChannel(CHANNEL_ID, "Channel One", importanceLow)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.setShowBadge(true)
            notificationChannel.description = message
            //            notificationChannel.setSound(sound, Notification.AUDIO_ATTRIBUTES_DEFAULT);
            notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            //            notificationChannel.setImportance(NotificationManager.IMPORTANCE_HIGH);
            //            grantUriPermission("com.android.systemui", sound,
            //                    Intent.FLAG_GRANT_READ_URI_PERMISSION);
            //            mNotificationManager.createNotificationChannel(notificationChannel);


        }
        val now = Date()
        val uniqueId = now.time
        resultIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val mNotificationBuilder = Notification.Builder(this)
            .setSmallIcon(R.mipmap.app_icon)
            .setContentTitle(title)
            .setOngoing(false)
            .setContentText(message)
            .setAutoCancel(true)
            .setSound(sound)
            .setWhen(System.currentTimeMillis())
            .setShowWhen(true)
            .setContentIntent(resultIntent)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mNotificationBuilder.setChannelId(CHANNEL_ID)
            mNotificationManager.createNotificationChannel(notificationChannel)

        }
        mNotificationManager.notify(uniqueId.toInt(), mNotificationBuilder.build())
    }

    /* @SuppressLint("ObsoleteSdkInt")
     private fun getNotificationIcon(): Int {
         val useWhiteIcon = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
 // return if (useWhiteIcon) R.drawable.ic_noti_trans else R.mipmap.ic_launcher
         return if (useWhiteIcon) R.drawable.app_icon else R.mipmap.app_icon
     }*/
}
