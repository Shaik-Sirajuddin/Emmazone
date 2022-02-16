package com.live.emmazone.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.live.emmazone.R
import com.live.emmazone.interfaces.OnAcceptRejectListener
import com.live.emmazone.interfaces.OnPopupClick
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class AppUtils {

    companion object {


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



    }

}