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
import com.live.emmazone.response_model.*
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
            service.signUp(hashMap, image)
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


    fun loginApi(activity: Activity, isDialogShow: Boolean, hashMap: HashMap<String, String>) {
        if (activity.checkIfHasNetwork()) {
            RestObservable.loading(activity, isDialogShow)
            service.login(hashMap)
                .enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
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

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        mResponse.value = RestObservable.error(activity, t)
                    }

                })
        } else {
            AppUtils.showMsgOnlyWithClick(activity,
                activity.getString(R.string.no_internet_connection), object : OnPopupClick {
                    override fun onPopupClickListener() {
                        loginApi(activity, isDialogShow, hashMap)
                    }
                })
        }
    }


    fun termsConditionApi(activity: Activity, isDialogShow: Boolean) {
        if (activity.checkIfHasNetwork()) {
            RestObservable.loading(activity, isDialogShow)
            service.termsCondition()
                .enqueue(object : Callback<TermsConditionResponse> {
                    override fun onResponse(
                        call: Call<TermsConditionResponse>,
                        response: Response<TermsConditionResponse>
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

                    override fun onFailure(call: Call<TermsConditionResponse>, t: Throwable) {
                        mResponse.value = RestObservable.error(activity, t)
                    }

                })
        } else {
            AppUtils.showMsgOnlyWithClick(activity,
                activity.getString(R.string.no_internet_connection), object : OnPopupClick {
                    override fun onPopupClickListener() {
                        termsConditionApi(activity, isDialogShow)
                    }
                })
        }
    }


    fun privacyPolicyApi(activity: Activity, isDialogShow: Boolean) {
        if (activity.checkIfHasNetwork()) {
            RestObservable.loading(activity, isDialogShow)
            service.privacyPolicy()
                .enqueue(object : Callback<PrivacyPolicyResponse> {
                    override fun onResponse(
                        call: Call<PrivacyPolicyResponse>,
                        response: Response<PrivacyPolicyResponse>
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

                    override fun onFailure(call: Call<PrivacyPolicyResponse>, t: Throwable) {
                        mResponse.value = RestObservable.error(activity, t)
                    }

                })
        } else {
            AppUtils.showMsgOnlyWithClick(activity,
                activity.getString(R.string.no_internet_connection), object : OnPopupClick {
                    override fun onPopupClickListener() {
                        privacyPolicyApi(activity, isDialogShow)
                    }
                })
        }
    }


    fun forgotPassApi(activity: Activity, isDialogShow: Boolean, hashMap: HashMap<String, String>) {
        if (activity.checkIfHasNetwork()) {
            RestObservable.loading(activity, isDialogShow)
            service.forgotPassword(hashMap)
                .enqueue(object : Callback<ForgotPasswordResponse> {
                    override fun onResponse(
                        call: Call<ForgotPasswordResponse>,
                        response: Response<ForgotPasswordResponse>
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

                    override fun onFailure(call: Call<ForgotPasswordResponse>, t: Throwable) {
                        mResponse.value = RestObservable.error(activity, t)
                    }

                })
        } else {
            AppUtils.showMsgOnlyWithClick(activity,
                activity.getString(R.string.no_internet_connection), object : OnPopupClick {
                    override fun onPopupClickListener() {
                        forgotPassApi(activity, isDialogShow, hashMap)
                    }
                })
        }
    }


    fun categoryListApi(activity: Activity, isDialogShow: Boolean) {
        if (activity.checkIfHasNetwork()) {
            RestObservable.loading(activity, isDialogShow)
            service.categoryList()
                .enqueue(object : Callback<CategoryListResponse> {
                    override fun onResponse(
                        call: Call<CategoryListResponse>,
                        response: Response<CategoryListResponse>
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

                    override fun onFailure(call: Call<CategoryListResponse>, t: Throwable) {
                        mResponse.value = RestObservable.error(activity, t)
                    }

                })
        } else {
            AppUtils.showMsgOnlyWithClick(activity,
                activity.getString(R.string.no_internet_connection), object : OnPopupClick {
                    override fun onPopupClickListener() {
                        categoryListApi(activity, isDialogShow)
                    }
                })
        }
    }


    fun addShopApi(
        activity: Activity, isDialogShow: Boolean,
        hashMap: HashMap<String, RequestBody>, image: MultipartBody.Part
    ) {
        if (activity.checkIfHasNetwork()) {
            RestObservable.loading(activity, isDialogShow)
            service.addShop(hashMap, image)
                .enqueue(object : Callback<AddShopResponse> {
                    override fun onResponse(
                        call: Call<AddShopResponse>,
                        response: Response<AddShopResponse>
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

                    override fun onFailure(call: Call<AddShopResponse>, t: Throwable) {
                        mResponse.value = RestObservable.error(activity, t)
                    }

                })
        } else {
            AppUtils.showMsgOnlyWithClick(activity,
                activity.getString(R.string.no_internet_connection), object : OnPopupClick {
                    override fun onPopupClickListener() {
                        addShopApi(activity, isDialogShow, hashMap, image)
                    }
                })
        }
    }

    fun changePassApi(activity: Activity, isDialogShow: Boolean, hashMap: HashMap<String, String>) {
        if (activity.checkIfHasNetwork()) {
            RestObservable.loading(activity, isDialogShow)
            service.changePassword(hashMap)
                .enqueue(object : Callback<ChangePasswordResponse> {
                    override fun onResponse(
                        call: Call<ChangePasswordResponse>,
                        response: Response<ChangePasswordResponse>
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

                    override fun onFailure(call: Call<ChangePasswordResponse>, t: Throwable) {
                        mResponse.value = RestObservable.error(activity, t)
                    }

                })
        } else {
            AppUtils.showMsgOnlyWithClick(activity,
                activity.getString(R.string.no_internet_connection), object : OnPopupClick {
                    override fun onPopupClickListener() {
                        forgotPassApi(activity, isDialogShow, hashMap)
                    }
                })
        }
    }



}