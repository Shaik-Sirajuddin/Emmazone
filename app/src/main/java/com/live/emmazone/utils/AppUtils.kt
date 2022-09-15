package com.live.emmazone.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import com.live.emmazone.BuildConfig
import com.live.emmazone.R
import com.live.emmazone.interfaces.OnAcceptRejectListener
import com.live.emmazone.interfaces.OnPopupClick
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class AppUtils {

    companion object {

        private const val SECOND_MILLIS = 1000
        private const val MINUTE_MILLIS = 60 * SECOND_MILLIS
        private const val HOUR_MILLIS = 60 * MINUTE_MILLIS
        private const val DAY_MILLIS = 24 * HOUR_MILLIS
        private const val WEEK_MILLIS = 7 * DAY_MILLIS
        private const val MONTH_MILLIS = 4 * WEEK_MILLIS.toLong()
        private const val YEAR_MILLIS = 12 * MONTH_MILLIS


        @SuppressLint("ClickableViewAccessibility")
        fun scrollOff(v: View) {
            v.setOnTouchListener { view, motionEvent ->
                /*if (view.id == R.id.v) {*/
                view.parent.requestDisallowInterceptTouchEvent(true)
                when (motionEvent.action and MotionEvent.ACTION_MASK) {
                    MotionEvent.ACTION_UP -> view.parent.requestDisallowInterceptTouchEvent(false)
                    /*}*/
                }

                false
            }
        }

        //--------------------------Keyboard Hide ----------------------//
        fun hideSoftKeyboard(activity: Activity) {
            val inputMethodManager = activity.getSystemService(
                Activity.INPUT_METHOD_SERVICE
            ) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                activity.currentFocus?.windowToken, 0
            )
        }


        //----------------------------OK Click Dialog--------------------
        fun showMsgOnlyWithoutClick(activity: Context, msg: String) {
            val okDialog = Dialog(activity, R.style.Theme_Dialog)
            okDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            okDialog.setContentView(R.layout.dialog_common_with_ok)
            okDialog.window!!.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            okDialog.setCancelable(false)
            okDialog.setCanceledOnTouchOutside(false)
            okDialog.window!!.setGravity(Gravity.CENTER)
            okDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


            val tvOk = okDialog.findViewById<TextView>(R.id.tv_ok)
            val tvMsg = okDialog.findViewById<TextView>(R.id.tv_msg)

            tvMsg.text = msg


            tvOk.setOnClickListener {
                okDialog.dismiss()
            }

            okDialog.show()
        }

        //-----------------------OK Click With Listener---------------------------
        fun showMsgOnlyWithClick(activity: Context, msg: String, listener: OnPopupClick) {
            val okListenerDualog = Dialog(activity, R.style.Theme_Dialog)
            okListenerDualog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            okListenerDualog.setContentView(R.layout.dialog_common_with_ok)
            okListenerDualog.window!!.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            okListenerDualog.setCancelable(false)
            okListenerDualog.setCanceledOnTouchOutside(false)
            okListenerDualog.window!!.setGravity(Gravity.CENTER)
            okListenerDualog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


            val tvOk = okListenerDualog.findViewById<TextView>(R.id.tv_ok)
            val tvMsg = okListenerDualog.findViewById<TextView>(R.id.tv_msg)

            tvMsg.text = msg


            tvOk.setOnClickListener {
                okListenerDualog.dismiss()
                listener.onPopupClickListener()
            }

            okListenerDualog.show()
        }


        //-----------------------Accept Reject Dialog----------------------
        fun acceptRejectPopup(
            activity: Context, msg: String,
            type: String, listener: OnAcceptRejectListener
        ) {
            val okListenerDualog = Dialog(activity, R.style.Theme_Dialog)
            okListenerDualog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            okListenerDualog.setContentView(R.layout.dialog_common_accept_reject)
            okListenerDualog.window!!.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            okListenerDualog.setCancelable(true)
            okListenerDualog.setCanceledOnTouchOutside(false)
            okListenerDualog.window!!.setGravity(Gravity.CENTER)
            okListenerDualog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


            val tvOk = okListenerDualog.findViewById<TextView>(R.id.tv_ok)
            val tvCancel = okListenerDualog.findViewById<TextView>(R.id.tv_cancel)
            val tvMsg = okListenerDualog.findViewById<TextView>(R.id.tv_msg)

            tvMsg.text = msg
            tvCancel.setOnClickListener {
                okListenerDualog.dismiss()


            }

            tvOk.setOnClickListener {
                okListenerDualog.dismiss()
                listener.onAcceptRejectPopupListener(type)
            }

            okListenerDualog.show()
        }


        fun toDate(dateAt: String, format: String): String {
            val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            val output = SimpleDateFormat(format)

            var d: Date? = null
            try {
                d = input.parse(dateAt)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return output.format(d)
        }


        //------------------------Return Time in String------------------//
        fun secondsToTime(seconds: Long, format: String): String {
            val sdf = SimpleDateFormat(format, Locale.getDefault())
            val milliSeconds = seconds*1000 // if "timeInMillis" in Seconds than use it.
            return sdf.format(milliSeconds)
        }

        fun cardTypeModelSet(): ArrayList<CardTypeModel>? {
            val listOfPattern: ArrayList<CardTypeModel> = ArrayList<CardTypeModel>()
            val cardTypeModel = CardTypeModel()
            cardTypeModel.setName("Visa")
            cardTypeModel.setRegx("^4[0-9]$")
            cardTypeModel.setType("0")
            listOfPattern.add(cardTypeModel)
            val cardTypeModel2 = CardTypeModel()
            cardTypeModel2.setName("Master")
            cardTypeModel2.setRegx("^5[1-5]$")
            cardTypeModel2.setType("1")
            listOfPattern.add(cardTypeModel2)
            return listOfPattern
        }


        fun getCardType(number: String): String? {
            var type: String? = ""
            for (i in cardTypeModelSet()!!.indices) {
                if (number.substring(0, 2).matches(cardTypeModelSet()!![i].regx.toRegex())) {
                    type = cardTypeModelSet()!![i].type
                }
            }
            return type
        }

        //------------------------Return Time in String------------------//
        fun milliSecondsToTime(milliSeconds: Long, format: String): String {
            val sdf = SimpleDateFormat(format, Locale.getDefault())
            return sdf.format(milliSeconds)
        }

        fun getDateFromUTCTimestamp(mTimestamp: Long, mDateFormat: String?): String? {
            var date: String? = null
            try {
                val cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                cal.timeInMillis = mTimestamp * 1000L
                date =
                    android.text.format.DateFormat.format(mDateFormat, cal.timeInMillis).toString()
                val formatter = SimpleDateFormat(mDateFormat)
                formatter.timeZone = TimeZone.getTimeZone("UTC")
                val value = formatter.parse(date)
                val dateFormatter = SimpleDateFormat(mDateFormat)
                dateFormatter.timeZone = TimeZone.getDefault()
                date = dateFormatter.format(value)
                return date
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
            return date
        }

        // get date and time according to GMT
        fun getTimeTest(timeStamp: Long): String? {
            return try {
                val sdf = SimpleDateFormat("hh:mm a")
                sdf.timeZone= TimeZone.getTimeZone("GMT")
                val netDate = Date(timeStamp * 1000L)
                sdf.format(netDate)
            } catch (ex: java.lang.Exception) {
                "xx"
            }
        }

        fun getDateTime(timestamp: Long):String{
            return try {
                val sdf = SimpleDateFormat("dd-MMM-yyyy")
                sdf.timeZone= TimeZone.getTimeZone("GMT")
                val netDate = Date(timestamp * 1000L)
                sdf.format(netDate)
            } catch (ex: java.lang.Exception) {
                "xx"
            }
        }

        fun getNotificationTimeAgo(timeMillis: Long): String? {
            var time = timeMillis
            if (time < 1000000000000L) {
                time *= 1000
            }

            val now = System.currentTimeMillis()

            val diff = now - time
            when {
                diff < 2 * SECOND_MILLIS -> {
                    return "Just now"
                }
                diff < 60 * SECOND_MILLIS -> {
                    return (diff / SECOND_MILLIS).toString() + " sec"
                }
                diff < 2 * MINUTE_MILLIS -> {
                    return "1 min"
                }
                diff < 50 * MINUTE_MILLIS -> {
                    return (diff / MINUTE_MILLIS).toString() + " min"
                }
                diff < 90 * MINUTE_MILLIS -> {
                    return "an hour"
                }
                diff < 24 * HOUR_MILLIS -> {
                    return (diff / HOUR_MILLIS).toString() + " hours"
                }
                diff < 48 * HOUR_MILLIS -> {
                    return "1 day"
                }
                diff < 7 * DAY_MILLIS -> {
                    return (diff / DAY_MILLIS).toString() + " days";
                }
                diff < 2 * WEEK_MILLIS.toLong() -> {
                    return "1 week"
                }
                diff < 4 * WEEK_MILLIS.toLong() -> {
                    return (diff / WEEK_MILLIS.toLong()).toString() + " week"
                }
                diff < 2 * MONTH_MILLIS -> {
                    return "1 month"
                }
                diff < 12 * MONTH_MILLIS -> {
                    return (diff / MONTH_MILLIS).toString() + " month"
                }
                diff < 2 * YEAR_MILLIS -> {
                    return "a year"
                }
                else -> {
                    return (diff / YEAR_MILLIS).toString() + " years"
                }
            }
        }
        fun Activity.showToast(message : String){
            Toast.makeText( this,message, Toast.LENGTH_SHORT).show()
        }
        fun Activity.openGoogleMaps(latitude : String , longitude : String){
            val gmmIntentUri =
                Uri.parse("google.navigation:q=${latitude},${longitude}")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }
        fun Activity.getURLForResource(resourceId: Int): String {
            //use BuildConfig.APPLICATION_ID instead of R.class.getPackage().getName() if both are not same
            return Uri.parse("android.resource://" + BuildConfig.APPLICATION_ID + "/" + resourceId)
                .toString()
        }
    }

}
