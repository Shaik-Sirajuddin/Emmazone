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
                        changePassApi(activity, isDialogShow, hashMap)
                    }
                })
        }
    }

    fun notificationListApi(activity: Activity, isDialogShow: Boolean) {
        if (activity.checkIfHasNetwork()) {
            RestObservable.loading(activity, isDialogShow)
            service.notificationList()
                .enqueue(object : Callback<NotificatioListingResponse> {
                    override fun onResponse(
                        call: Call<NotificatioListingResponse>,
                        response: Response<NotificatioListingResponse>
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

                    override fun onFailure(call: Call<NotificatioListingResponse>, t: Throwable) {
                        mResponse.value = RestObservable.error(activity, t)
                    }

                })
        } else {
            AppUtils.showMsgOnlyWithClick(activity,
                activity.getString(R.string.no_internet_connection), object : OnPopupClick {
                    override fun onPopupClickListener() {
                        notificationListApi(activity, isDialogShow)
                    }
                })
        }
    }

    fun notificationStatusApi(activity: Activity, isDialogShow: Boolean, hashMap: HashMap<String, String>) {
        if (activity.checkIfHasNetwork()) {
            RestObservable.loading(activity, isDialogShow)
            service.notificationStatus(hashMap)
                .enqueue(object : Callback<NotificationStatusResponse> {
                    override fun onResponse(
                        call: Call<NotificationStatusResponse>,
                        response: Response<NotificationStatusResponse>
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

                    override fun onFailure(call: Call<NotificationStatusResponse>, t: Throwable) {
                        mResponse.value = RestObservable.error(activity, t)
                    }

                })
        } else {
            AppUtils.showMsgOnlyWithClick(activity,
                activity.getString(R.string.no_internet_connection), object : OnPopupClick {
                    override fun onPopupClickListener() {
                        notificationStatusApi(activity, isDialogShow, hashMap)
                    }
                })
        }
    }

    fun profileApi(activity: Activity, isDialogShow: Boolean) {
        if (activity.checkIfHasNetwork()) {
            RestObservable.loading(activity, isDialogShow)
            service.profile()
                .enqueue(object : Callback<ProfileResponse> {
                    override fun onResponse(
                        call: Call<ProfileResponse>,
                        response: Response<ProfileResponse>
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

                    override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                        mResponse.value = RestObservable.error(activity, t)
                    }

                })
        } else {
            AppUtils.showMsgOnlyWithClick(activity,
                activity.getString(R.string.no_internet_connection), object : OnPopupClick {
                    override fun onPopupClickListener() {
                        profileApi(activity, isDialogShow)
                    }
                })
        }
    }

    fun editProfileApi(activity: Activity, isDialogShow: Boolean, hashMap: HashMap<String, RequestBody>, image: MultipartBody.Part) {
        if (activity.checkIfHasNetwork()) {
            RestObservable.loading(activity, isDialogShow)
            service.editProfile(hashMap,image)
                .enqueue(object : Callback<EditProfileResponse> {
                    override fun onResponse(
                        call: Call<EditProfileResponse>,
                        response: Response<EditProfileResponse>
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

                    override fun onFailure(call: Call<EditProfileResponse>, t: Throwable) {
                        mResponse.value = RestObservable.error(activity, t)
                    }

                })
        } else {
            AppUtils.showMsgOnlyWithClick(activity,
                activity.getString(R.string.no_internet_connection), object : OnPopupClick {
                    override fun onPopupClickListener() {
                        editProfileApi(activity, isDialogShow, hashMap,image)
                    }
                })
        }
    }

    fun addAddressApi(activity: Activity, isDialogShow: Boolean, hashMap: HashMap<String, String>) {
        if (activity.checkIfHasNetwork()) {
            RestObservable.loading(activity, isDialogShow)
            service.addAddress(hashMap)
                .enqueue(object : Callback<AddNewAddressResponse> {
                    override fun onResponse(
                        call: Call<AddNewAddressResponse>,
                        response: Response<AddNewAddressResponse>
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

                    override fun onFailure(call: Call<AddNewAddressResponse>, t: Throwable) {
                        mResponse.value = RestObservable.error(activity, t)
                    }

                })
        } else {
            AppUtils.showMsgOnlyWithClick(activity,
                activity.getString(R.string.no_internet_connection), object : OnPopupClick {
                    override fun onPopupClickListener() {
                        addAddressApi(activity, isDialogShow, hashMap)
                    }
                })
        }
    }

    fun addressListApi(activity: Activity, isDialogShow: Boolean) {
        if (activity.checkIfHasNetwork()) {
            RestObservable.loading(activity, isDialogShow)
            service.addressList()
                .enqueue(object : Callback<AddressListResponse> {
                    override fun onResponse(
                        call: Call<AddressListResponse>,
                        response: Response<AddressListResponse>
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

                    override fun onFailure(call: Call<AddressListResponse>, t: Throwable) {
                        mResponse.value = RestObservable.error(activity, t)
                    }

                })
        } else {
            AppUtils.showMsgOnlyWithClick(activity,
                activity.getString(R.string.no_internet_connection), object : OnPopupClick {
                    override fun onPopupClickListener() {
                        addressListApi(activity, isDialogShow)
                    }
                })
        }
    }

    fun shopListApi(activity: Activity, isDialogShow: Boolean, hashMap: HashMap<String, String>) {
        if (activity.checkIfHasNetwork()) {
            RestObservable.loading(activity, isDialogShow)
            service.shopListing(hashMap)
                .enqueue(object : Callback<ShopListingResponse> {
                    override fun onResponse(
                        call: Call<ShopListingResponse>,
                        response: Response<ShopListingResponse>
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

                    override fun onFailure(call: Call<ShopListingResponse>, t: Throwable) {
                        mResponse.value = RestObservable.error(activity, t)
                    }

                })
        } else {
            AppUtils.showMsgOnlyWithClick(activity,
                activity.getString(R.string.no_internet_connection), object : OnPopupClick {
                    override fun onPopupClickListener() {
                        shopListApi(activity, isDialogShow, hashMap)
                    }
                })
        }
    }

    fun addFavApi(activity: Activity, isDialogShow: Boolean, hashMap: HashMap<String, String>) {
        if (activity.checkIfHasNetwork()) {
            RestObservable.loading(activity, isDialogShow)
            service.favShop(hashMap)
                .enqueue(object : Callback<AddFavouriteResponse> {
                    override fun onResponse(
                        call: Call<AddFavouriteResponse>,
                        response: Response<AddFavouriteResponse>
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

                    override fun onFailure(call: Call<AddFavouriteResponse>, t: Throwable) {
                        mResponse.value = RestObservable.error(activity, t)
                    }

                })
        } else {
            AppUtils.showMsgOnlyWithClick(activity,
                activity.getString(R.string.no_internet_connection), object : OnPopupClick {
                    override fun onPopupClickListener() {
                        addFavApi(activity, isDialogShow, hashMap)
                    }
                })
        }
    }

    fun deleteAddressApi(activity: Activity, isDialogShow: Boolean, hashMap: HashMap<String, String>) {
        if (activity.checkIfHasNetwork()) {
            RestObservable.loading(activity, isDialogShow)
            service.deleteAddress(hashMap)
                .enqueue(object : Callback<DeleteAddressResponse> {
                    override fun onResponse(
                        call: Call<DeleteAddressResponse>,
                        response: Response<DeleteAddressResponse>
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

                    override fun onFailure(call: Call<DeleteAddressResponse>, t: Throwable) {
                        mResponse.value = RestObservable.error(activity, t)
                    }

                })
        } else {
            AppUtils.showMsgOnlyWithClick(activity,
                activity.getString(R.string.no_internet_connection), object : OnPopupClick {
                    override fun onPopupClickListener() {
                        deleteAddressApi(activity, isDialogShow, hashMap)
                    }
                })
        }
    }

    fun addProductApi(activity: Activity, isDialogShow: Boolean, hashMap: HashMap<String, String>) {
        if (activity.checkIfHasNetwork()) {
            RestObservable.loading(activity, isDialogShow)
            service.addProduct(hashMap)
                .enqueue(object : Callback<AddProductResponse> {
                    override fun onResponse(
                        call: Call<AddProductResponse>,
                        response: Response<AddProductResponse>
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

                    override fun onFailure(call: Call<AddProductResponse>, t: Throwable) {
                        mResponse.value = RestObservable.error(activity, t)
                    }

                })
        } else {
            AppUtils.showMsgOnlyWithClick(activity,
                activity.getString(R.string.no_internet_connection), object : OnPopupClick {
                    override fun onPopupClickListener() {
                        addProductApi(activity, isDialogShow, hashMap)
                    }
                })
        }
    }

    fun colorListApi(activity: Activity, isDialogShow: Boolean) {
        if (activity.checkIfHasNetwork()) {
            RestObservable.loading(activity, isDialogShow)
            service.colorList()
                .enqueue(object : Callback<ColorListResponse> {
                    override fun onResponse(
                        call: Call<ColorListResponse>,
                        response: Response<ColorListResponse>
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

                    override fun onFailure(call: Call<ColorListResponse>, t: Throwable) {
                        mResponse.value = RestObservable.error(activity, t)
                    }

                })
        } else {
            AppUtils.showMsgOnlyWithClick(activity,
                activity.getString(R.string.no_internet_connection), object : OnPopupClick {
                    override fun onPopupClickListener() {
                        colorListApi(activity, isDialogShow)
                    }
                })
        }
    }

    fun sizeListApi(activity: Activity, isDialogShow: Boolean) {
        if (activity.checkIfHasNetwork()) {
            RestObservable.loading(activity, isDialogShow)
            service.sizeList()
                .enqueue(object : Callback<SizeListResponse> {
                    override fun onResponse(
                        call: Call<SizeListResponse>,
                        response: Response<SizeListResponse>
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

                    override fun onFailure(call: Call<SizeListResponse>, t: Throwable) {
                        mResponse.value = RestObservable.error(activity, t)
                    }

                })
        } else {
            AppUtils.showMsgOnlyWithClick(activity,
                activity.getString(R.string.no_internet_connection), object : OnPopupClick {
                    override fun onPopupClickListener() {
                        sizeListApi(activity, isDialogShow)
                    }
                })
        }
    }
}