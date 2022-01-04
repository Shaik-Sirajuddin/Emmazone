package com.live.emmazone.view_models

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.live.emmazone.R
import com.live.emmazone.interfaces.OnPopupClick
import com.live.emmazone.net.RestApiInterface
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.ServiceGenerator
import com.live.emmazone.response_model.OtpResendResponse
import com.live.emmazone.response_model.OtpVerifyResponse
import com.live.emmazone.response_model.SignUpResponse
import com.live.emmazone.utils.AppUtils
import com.schunts.extensionfuncton.checkIfHasNetwork
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AppViewModel : ViewModel() {

    private var service = ServiceGenerator.createService(RestApiInterface::class.java)
    private var mResponse: MutableLiveData<RestObservable> = MutableLiveData()
    fun getResponse(): LiveData<RestObservable> {
        return mResponse
    }

    fun signUpApi(
        activity: Activity,
        isDialogShow: Boolean,
        hashMap: HashMap<String, RequestBody>,
        image: MultipartBody.Part
    ) {
        if (activity.checkIfHasNetwork()) {
            RestObservable.loading(activity, isDialogShow)
            service.signUp(hashMap,image)
                .enqueue(object : Callback<SignUpResponse> {
                    override fun onResponse(
                        call: Call<SignUpResponse>,
                        response: Response<SignUpResponse>
                    ) {
                        if (response.isSuccessful) {
                            mResponse.value = RestObservable.success(response.body()!!)
                        } else {
                            mResponse.value = RestObservable.errorWithSuccess(
                                activity,
                                response.code(),
                                response.errorBody()!!
                            )

                        }

                    }

                    override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                        mResponse.value = RestObservable.error(activity, t)
                    }

                })
        } else {
            AppUtils.showMsgOnlyWithClick(activity,
                activity.getString(R.string.no_internet_connection), object : OnPopupClick {
                    override fun onPopupClickListener() {
                        signUpApi(activity, isDialogShow, hashMap, image)
                    }
                })
        }
    }

    fun otpVerifyApi(
        activity: Activity,
        isDialogShow: Boolean,
        hashMap: HashMap<String, String>
    ) {
        if (activity.checkIfHasNetwork()) {
            RestObservable.loading(activity, isDialogShow)
            service.otpVerify(hashMap)
                .enqueue(object : Callback<OtpVerifyResponse> {
                    override fun onResponse(
                        call: Call<OtpVerifyResponse>,
                        response: Response<OtpVerifyResponse>
                    ) {
                        if (response.isSuccessful) {
                            mResponse.value = RestObservable.success(response.body()!!)
                        } else {
                            mResponse.value = RestObservable.errorWithSuccess(
                                activity,
                                response.code(),
                                response.errorBody()!!
                            )

                        }

                    }

                    override fun onFailure(call: Call<OtpVerifyResponse>, t: Throwable) {
                        mResponse.value = RestObservable.error(activity, t)
                    }

                })
        } else {
            AppUtils.showMsgOnlyWithClick(activity,
                activity.getString(R.string.no_internet_connection), object : OnPopupClick {
                    override fun onPopupClickListener() {
                        otpVerifyApi(activity, isDialogShow, hashMap)
                    }
                })
        }
    }

    fun resendOtpApi(
        activity: Activity,
        isDialogShow: Boolean
    ) {
        if (activity.checkIfHasNetwork()) {
            RestObservable.loading(activity, isDialogShow)
            service.resendOtp()
                .enqueue(object : Callback<OtpResendResponse> {
                    override fun onResponse(
                        call: Call<OtpResendResponse>,
                        response: Response<OtpResendResponse>
                    ) {
                        if (response.isSuccessful) {
                            mResponse.value = RestObservable.success(response.body()!!)
                        } else {
                            mResponse.value = RestObservable.errorWithSuccess(
                                activity,
                                response.code(),
                                response.errorBody()!!
                            )

                        }

                    }

                    override fun onFailure(call: Call<OtpResendResponse>, t: Throwable) {
                        mResponse.value = RestObservable.error(activity, t)
                    }

                })
        } else {
            AppUtils.showMsgOnlyWithClick(activity,
                activity.getString(R.string.no_internet_connection), object : OnPopupClick {
                    override fun onPopupClickListener() {
                        resendOtpApi(activity, isDialogShow)
                    }
                })
        }
    }

}