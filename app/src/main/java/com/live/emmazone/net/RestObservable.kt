package com.live.emmazone.net


import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import com.google.gson.JsonSyntaxException
import com.live.emmazone.R
import com.live.emmazone.activities.auth.LoginActivity
import com.live.emmazone.extensionfuncton.clearPreferences
import com.live.emmazone.utils.AppUtils
import com.live.emmazone.utils.ProgressDialog
import com.schunts.extensionfuncton.toast
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException


class RestObservable(
    val status: Status,
    val data: Any?,
    val error: Any?
) {


    companion object {
        private var progressDialog: ProgressDialog? = null
        fun loading(activity: Activity, isDialogShow: Boolean): RestObservable {
            Log.e("REST", "Loading")
            if (isDialogShow) {
                if (progressDialog != null && progressDialog!!.isShowing) {
                    progressDialog!!.dismiss()
                }
                progressDialog = ProgressDialog.create(
                    activity, "Please wait..",
                    indeterminate = true,
                    cancelable = false,
                    show = true,
                    cancelListener = null
                )
                progressDialog!!.show()
            }
            return RestObservable(Status.LOADING, null, null)
        }

        fun success(data: Any): RestObservable {
            Log.e("REST", data.toString())
            if (progressDialog != null && progressDialog!!.isShowing) {
                progressDialog!!.dismiss()
            }
            return RestObservable(Status.SUCCESS, data, null)
            /* val response: BaseResponse = data as BaseResponse
             if (response.code == 200) {
                 return RestObservable(Status.SUCCESS, data, null)
             } else {
                 return RestObservable(Status.ERROR, null, response.message)
             }*/
        }

        fun error(activity: Activity, error: Throwable): RestObservable {
            Log.e("REST", "Error")
            if (progressDialog != null && progressDialog!!.isShowing) {
                progressDialog!!.dismiss()
            }
            try {
                // We had non-200 http error
                if (error is HttpException) {
                    val httpException = error as HttpException
                    val response = httpException.response()
                    val errorMessage = callErrorMethod(response!!.errorBody())
                    Log.i(TAG, error.message() + " // / " + errorMessage)
                    if (response.code() == 401) {

                        val intent = Intent(activity, LoginActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        activity.startActivity(intent)
                        activity.finish()
                        clearPreferences()
                        toast(activity.getString(R.string.session_expired))
                    } else {
                        AppUtils.showMsgOnlyWithoutClick(activity, errorMessage)
                    }
                    return RestObservable(Status.ERROR, null, errorMessage)
                } else if (error is JsonSyntaxException) {
                    val httpException = error as HttpException
                    val response = httpException.response()
                    val errorMessage = callErrorMethod(response!!.errorBody())
                    Log.i(TAG, error.message() + " // / " + errorMessage)
                    AppUtils.showMsgOnlyWithoutClick(activity, errorMessage)
                    return RestObservable(Status.ERROR, null, errorMessage)
                }
                // A network error happened
                if (error is IOException) {
                    Log.i(TAG, error.message + " / " + error.javaClass)
                    AppUtils.showMsgOnlyWithoutClick(
                        activity,
                        activity.getString(R.string.unavle_to_connect_server)
                    )
                    return RestObservable(Status.ERROR, null, error)
                }

                Log.i(TAG, error.message + " / " + error.javaClass)
            } catch (e: Exception) {
                Log.i(TAG, e.message!!)
                AppUtils.showMsgOnlyWithoutClick(activity, e.message!!)
                return RestObservable(Status.ERROR, null, error)
            }

            return RestObservable(Status.ERROR, null, error)
        }

        fun errorWithSuccess(activity: Activity, errorCode: Int, errorBody: ResponseBody): RestObservable {
            val errorMessage = callErrorMethod(errorBody)
            Log.i(TAG, "error // / $errorMessage")

            if (progressDialog != null && progressDialog!!.isShowing) {
                progressDialog!!.dismiss()
            }

            if (errorCode == 401) {
                val intent = Intent(activity, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                activity.startActivity(intent)
                activity.finish()
                clearPreferences()
                toast(activity.getString(R.string.session_expired))
            }else{
                AppUtils.showMsgOnlyWithoutClick(activity, errorMessage)
            }

            return RestObservable(Status.ERROR, null, errorMessage)
        }

        private fun callErrorMethod(responseBody: ResponseBody?): String {

            val converter = ServiceGenerator.getRetrofit()
                .responseBodyConverter<RestError>(
                    RestError::class.java,
                    arrayOfNulls<Annotation>(0)
                )
            return try {
                val errorResponse = converter.convert(responseBody!!)
                val errorMessage = errorResponse!!.message
                errorMessage!!
            } catch (e: IOException) {
                e.toString()
            }

        }

        fun progressDialogDismiss(){
            if (progressDialog != null && progressDialog!!.isShowing) {
                progressDialog!!.dismiss()
            }
        }
    }

}
