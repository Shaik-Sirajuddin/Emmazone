package com.live.emmazone.view_models

import android.app.Activity
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.live.emmazone.R
import com.live.emmazone.interfaces.OnPopupClick
import com.live.emmazone.model.CartResponsModel
import com.live.emmazone.model.ShopProductDetailResponse
import com.live.emmazone.net.CartUpdateResponse
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
    var mResponse: MutableLiveData<RestObservable> = MutableLiveData()
    fun getResponse(): LiveData<RestObservable> {
        return mResponse
    }

    fun signUpApi(
        activity: Activity,
        isDialogShow: Boolean,
        hashMap: HashMap<String, RequestBody>,
        image: MultipartBody.Part?
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
                        Log.e("loginFail",mResponse.value?.error.toString())
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

    fun sellerShopDetailsApi(activity: Activity, isDialogShow: Boolean) {
        if (activity.checkIfHasNetwork()) {
            RestObservable.loading(activity, isDialogShow)
            service.sellerShopDetails()
                .enqueue(object : Callback<SellerShopDetailResponse> {
                    override fun onResponse(
                        call: Call<SellerShopDetailResponse>,
                        response: Response<SellerShopDetailResponse>
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

                    override fun onFailure(call: Call<SellerShopDetailResponse>, t: Throwable) {
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
    // Changes-------------------------------------------------------
    fun deleteProductApi(activity: Activity, isDialogShow: Boolean, productId: String) {
        if (activity.checkIfHasNetwork()) {
            RestObservable.loading(activity, isDialogShow)
            service.deleteProduct(productId)
                .enqueue(object : Callback<CommonResponse> {
                    override fun onResponse(
                        call: Call<CommonResponse>,
                        response: Response<CommonResponse>
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

                    override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
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

    ///----------------------------------------------------------
    fun deleteProductGroupApi(activity: Activity, isDialogShow: Boolean, groupId: String) {
        if (activity.checkIfHasNetwork()) {
            RestObservable.loading(activity, isDialogShow)
            service.deleteProductGroup(groupId)
                .enqueue(object : Callback<CommonResponse> {
                    override fun onResponse(
                        call: Call<CommonResponse>,
                        response: Response<CommonResponse>
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

                    override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
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

    fun selectedCategoryListApi(activity: Activity, isDialogShow: Boolean) {
        if (activity.checkIfHasNetwork()) {
            RestObservable.loading(activity, isDialogShow)
            service.selectedCategoryList()
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
    fun addSize(
        activity: Activity,
        isDialogShow: Boolean,
        hashMap: HashMap<String, RequestBody>
    ){
        if (activity.checkIfHasNetwork()) {
            RestObservable.loading(activity, isDialogShow)
            service.addSize(hashMap)
                .enqueue(object : Callback<AddCategoryResponse> {
                    override fun onResponse(
                        call: Call<AddCategoryResponse>,
                        response: Response<AddCategoryResponse>
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

                    override fun onFailure(call: Call<AddCategoryResponse>, t: Throwable) {
                        mResponse.value = RestObservable.error(activity, t)
                    }

                })
        } else {
            AppUtils.showMsgOnlyWithClick(activity,
                activity.getString(R.string.no_internet_connection), object : OnPopupClick {
                    override fun onPopupClickListener() {
                        addSize(activity, isDialogShow, hashMap)
                    }
                })
        }
    }
    fun addColor(
        activity: Activity,
        isDialogShow: Boolean,
        hashMap: HashMap<String, RequestBody>,
    ){
        if (activity.checkIfHasNetwork()) {
            RestObservable.loading(activity, isDialogShow)
            service.addColor(hashMap)
                .enqueue(object : Callback<AddCategoryResponse> {
                    override fun onResponse(
                        call: Call<AddCategoryResponse>,
                        response: Response<AddCategoryResponse>
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

                    override fun onFailure(call: Call<AddCategoryResponse>, t: Throwable) {
                        mResponse.value = RestObservable.error(activity, t)
                    }

                })
        } else {
            AppUtils.showMsgOnlyWithClick(activity,
                activity.getString(R.string.no_internet_connection), object : OnPopupClick {
                    override fun onPopupClickListener() {
                        addColor(activity, isDialogShow, hashMap)
                    }
                })
        }
    }
    fun addCategory(
        activity: Activity,
        isDialogShow: Boolean,
        hashMap: HashMap<String, RequestBody>,
        image: MultipartBody.Part
    ){
        if (activity.checkIfHasNetwork()) {
            RestObservable.loading(activity, isDialogShow)
            service.addCategory(hashMap, image)
                .enqueue(object : Callback<AddCategoryResponse> {
                    override fun onResponse(
                        call: Call<AddCategoryResponse>,
                        response: Response<AddCategoryResponse>
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

                    override fun onFailure(call: Call<AddCategoryResponse>, t: Throwable) {
                        mResponse.value = RestObservable.error(activity, t)
                    }

                })
        } else {
            AppUtils.showMsgOnlyWithClick(activity,
                activity.getString(R.string.no_internet_connection), object : OnPopupClick {
                    override fun onPopupClickListener() {
                        addCategory(activity, isDialogShow, hashMap, image)
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
                .enqueue(object : Callback<NotificationListingResponse> {
                    override fun onResponse(
                        call: Call<NotificationListingResponse>,
                        response: Response<NotificationListingResponse>
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

                    override fun onFailure(call: Call<NotificationListingResponse>, t: Throwable) {
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

    fun notificationStatusApi(
        activity: Activity,
        isDialogShow: Boolean,
        hashMap: HashMap<String, String>
    ) {
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

    fun editProfileApi(
        activity: Activity,
        isDialogShow: Boolean,
        hashMap: HashMap<String, RequestBody>,
        image: MultipartBody.Part
    ) {
        if (activity.checkIfHasNetwork()) {
            RestObservable.loading(activity, isDialogShow)
            service.editProfile(hashMap, image)
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
                        editProfileApi(activity, isDialogShow, hashMap, image)
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

    fun deleteAddressApi(
        activity: Activity,
        isDialogShow: Boolean,
        hashMap: HashMap<String, String>
    ) {
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

    fun addProductApi(
        activity: Activity,
        isDialogShow: Boolean,
        hashMap: HashMap<String, RequestBody>,
    ) {
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

    fun logoutApi(
        activity: Activity,
        isDialogShow: Boolean
    ) {
        if (activity.checkIfHasNetwork()) {
            RestObservable.loading(activity, isDialogShow)
            service.logout()
                .enqueue(object : Callback<LogoutResponse> {
                    override fun onResponse(
                        call: Call<LogoutResponse>,
                        response: Response<LogoutResponse>
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

                    override fun onFailure(call: Call<LogoutResponse>, t: Throwable) {
                        mResponse.value = RestObservable.error(activity, t)
                    }

                })
        } else {
            AppUtils.showMsgOnlyWithClick(activity,
                activity.getString(R.string.no_internet_connection), object : OnPopupClick {
                    override fun onPopupClickListener() {
                        logoutApi(activity, isDialogShow)
                    }
                })
        }
    }

    fun addProductReview(
        activity: Activity,
        isDialogShow: Boolean,
        hashMap: HashMap<String, RequestBody> /* = java.util.HashMap<kotlin.String, okhttp3.RequestBody> */
    ){
        if (activity.checkIfHasNetwork()) {
            RestObservable.loading(activity, isDialogShow)
            service.addProductReview(hashMap)
                .enqueue(object : Callback<CommonResponse> {
                    override fun onResponse(
                        call: Call<CommonResponse>,
                        response: Response<CommonResponse>
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

                    override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
                        mResponse.value = RestObservable.error(activity, t)
                    }

                })
        } else {
            AppUtils.showMsgOnlyWithClick(activity,
                activity.getString(R.string.no_internet_connection), object : OnPopupClick {
                    override fun onPopupClickListener() {
                        addProductReview(activity, isDialogShow, hashMap)
                    }
                })
        }
    }
   fun getMyProductReview(
       activity: Activity,
       isDialogShow: Boolean,
       hashMap: HashMap<String, RequestBody> /* = java.util.HashMap<kotlin.String, okhttp3.RequestBody> */
   ){
       if (activity.checkIfHasNetwork()) {
           RestObservable.loading(activity, isDialogShow)
           service.getMyProductReview(hashMap)
               .enqueue(object : Callback<ProductReviewResponse> {
                   override fun onResponse(
                       call: Call<ProductReviewResponse>,
                       response: Response<ProductReviewResponse>
                   ) {
                       if (response.isSuccessful) {
                           Log.e("this",response.body().toString())
                           mResponse.value = RestObservable.success(response.body()!!)
                       } else {
                           mResponse.value = RestObservable.errorWithSuccess(
                               activity,
                               response.code(),
                               response.errorBody()!!
                           )

                       }

                   }

                   override fun onFailure(call: Call<ProductReviewResponse>, t: Throwable) {
                       mResponse.value = RestObservable.error(activity, t)
                   }

               })
       } else {
           AppUtils.showMsgOnlyWithClick(activity,
               activity.getString(R.string.no_internet_connection), object : OnPopupClick {
                   override fun onPopupClickListener() {
                       getMyProductReview(activity, isDialogShow, hashMap)
                   }
               })
       }
   }

    fun ratingApi(
        activity: Activity,
        isDialogShow: Boolean,
        hashMap: HashMap<String, String>
    ) {
        if (activity.checkIfHasNetwork()) {
            RestObservable.loading(activity, isDialogShow)
            service.rating(hashMap)
                .enqueue(object : Callback<RatingResponse> {
                    override fun onResponse(
                        call: Call<RatingResponse>,
                        response: Response<RatingResponse>
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

                    override fun onFailure(call: Call<RatingResponse>, t: Throwable) {
                        mResponse.value = RestObservable.error(activity, t)
                    }

                })
        } else {
            AppUtils.showMsgOnlyWithClick(activity,
                activity.getString(R.string.no_internet_connection), object : OnPopupClick {
                    override fun onPopupClickListener() {
                        ratingApi(activity, isDialogShow, hashMap)
                    }
                })
        }
    }


    fun categoryColorSizeApi(
        activity: Activity,
        isDialogShow: Boolean,
        hashMap: HashMap<String, String>
    ) {
        if (activity.checkIfHasNetwork()) {
            RestObservable.loading(activity, isDialogShow)
            service.categoryColorSize(hashMap)
                .enqueue(object : Callback<CategoryColorSizeResponse> {
                    override fun onResponse(
                        call: Call<CategoryColorSizeResponse>,
                        response: Response<CategoryColorSizeResponse>
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

                    override fun onFailure(call: Call<CategoryColorSizeResponse>, t: Throwable) {
                        mResponse.value = RestObservable.error(activity, t)
                    }

                })
        } else {
            AppUtils.showMsgOnlyWithClick(activity,
                activity.getString(R.string.no_internet_connection), object : OnPopupClick {
                    override fun onPopupClickListener() {
                        categoryColorSizeApi(activity, isDialogShow, hashMap)
                    }
                })
        }
    }


    fun addCardApi(activity: Activity, isDialogShow: Boolean, hashMap: HashMap<String, String>) {
        if (activity.checkIfHasNetwork()) {
            RestObservable.loading(activity, isDialogShow)
            service.addCard(hashMap)
                .enqueue(object : Callback<AddCardResponse> {
                    override fun onResponse(
                        call: Call<AddCardResponse>,
                        response: Response<AddCardResponse>
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

                    override fun onFailure(call: Call<AddCardResponse>, t: Throwable) {
                        mResponse.value = RestObservable.error(activity, t)
                    }

                })
        } else {
            AppUtils.showMsgOnlyWithClick(activity,
                activity.getString(R.string.no_internet_connection), object : OnPopupClick {
                    override fun onPopupClickListener() {
                        addCardApi(activity, isDialogShow, hashMap)
                    }
                })
        }
    }

    fun cardListApi(activity: Activity, isDialogShow: Boolean) {
        if (activity.checkIfHasNetwork()) {
            RestObservable.loading(activity, isDialogShow)
            service.cardList()
                .enqueue(object : Callback<CardListResponse> {
                    override fun onResponse(
                        call: Call<CardListResponse>,
                        response: Response<CardListResponse>
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

                    override fun onFailure(call: Call<CardListResponse>, t: Throwable) {
                        mResponse.value = RestObservable.error(activity, t)
                    }
                })
        } else {
            AppUtils.showMsgOnlyWithClick(activity,
                activity.getString(R.string.no_internet_connection), object : OnPopupClick {
                    override fun onPopupClickListener() {
                        cardListApi(activity, isDialogShow)
                    }
                })
        }
    }

    fun addAccountApi(activity: Activity, isDialogShow: Boolean, hashMap: HashMap<String, String>) {
        if (activity.checkIfHasNetwork()) {
            RestObservable.loading(activity, isDialogShow)
            service.addAccount(hashMap)
                .enqueue(object : Callback<AddBankResponse> {
                    override fun onResponse(
                        call: Call<AddBankResponse>,
                        response: Response<AddBankResponse>
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

                    override fun onFailure(call: Call<AddBankResponse>, t: Throwable) {
                        mResponse.value = RestObservable.error(activity, t)
                    }

                })
        } else {
            AppUtils.showMsgOnlyWithClick(activity,
                activity.getString(R.string.no_internet_connection), object : OnPopupClick {
                    override fun onPopupClickListener() {
                        addAccountApi(activity, isDialogShow, hashMap)
                    }
                })
        }
    }

    fun getProductWishList(activity: Activity, isDialogShow: Boolean) {
        if (activity.checkIfHasNetwork()) {
            RestObservable.loading(activity, isDialogShow)
            service.getWishProducts()
                .enqueue(object : Callback<SearchProductResponse> {
                    override fun onResponse(
                        call: Call<SearchProductResponse>,
                        response: Response<SearchProductResponse>
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

                    override fun onFailure(call: Call<SearchProductResponse>, t: Throwable) {
                        mResponse.value = RestObservable.error(activity, t)
                    }

                })
        } else {
            AppUtils.showMsgOnlyWithClick(activity,
                activity.getString(R.string.no_internet_connection), object : OnPopupClick {
                    override fun onPopupClickListener() {
                        getProductWishList(activity, isDialogShow)
                    }
                })
        }
    }
    fun likeOrDislikeProduct(activity: Activity, isDialogShow: Boolean, hashMap: HashMap<String, String>) {
        if (activity.checkIfHasNetwork()) {
            RestObservable.loading(activity, isDialogShow)
            service.likeOrDislikeProduct(hashMap)
                .enqueue(object : Callback<CommonResponse> {
                    override fun onResponse(
                        call: Call<CommonResponse>,
                        response: Response<CommonResponse>
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

                    override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
                        mResponse.value = RestObservable.error(activity, t)
                    }

                })
        } else {
            AppUtils.showMsgOnlyWithClick(activity,
                activity.getString(R.string.no_internet_connection), object : OnPopupClick {
                    override fun onPopupClickListener() {
                        likeOrDislikeProduct(activity, isDialogShow, hashMap)
                    }
                })
        }
    }
    fun wishListApi(activity: Activity, isDialogShow: Boolean, hashMap: HashMap<String, String>) {
        if (activity.checkIfHasNetwork()) {
            RestObservable.loading(activity, isDialogShow)
            service.wishList(hashMap)
                .enqueue(object : Callback<WishListResponse> {
                    override fun onResponse(
                        call: Call<WishListResponse>,
                        response: Response<WishListResponse>
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

                    override fun onFailure(call: Call<WishListResponse>, t: Throwable) {
                        mResponse.value = RestObservable.error(activity, t)
                    }

                })
        } else {
            AppUtils.showMsgOnlyWithClick(activity,
                activity.getString(R.string.no_internet_connection), object : OnPopupClick {
                    override fun onPopupClickListener() {
                        wishListApi(activity, isDialogShow, hashMap)
                    }
                })
        }
    }

    fun shopDetailApi(activity: Activity, isDialogShow: Boolean, hashMap: HashMap<String, String>) {
        if (activity.checkIfHasNetwork()) {
            RestObservable.loading(activity, isDialogShow)
            service.shopDetail(hashMap)
                .enqueue(object : Callback<ShopDetailResponse> {
                    override fun onResponse(
                        call: Call<ShopDetailResponse>,
                        response: Response<ShopDetailResponse>
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

                    override fun onFailure(call: Call<ShopDetailResponse>, t: Throwable) {
                        mResponse.value = RestObservable.error(activity, t)
                    }

                })
        } else {
            AppUtils.showMsgOnlyWithClick(activity,
                activity.getString(R.string.no_internet_connection), object : OnPopupClick {
                    override fun onPopupClickListener() {
                        shopDetailApi(activity, isDialogShow, hashMap)
                    }
                })
        }
    }

    fun shopProductDetailApi(
        activity: Activity,
        isDialogShow: Boolean,
        hashMap: HashMap<String, String>
    ) {
        if (activity.checkIfHasNetwork()) {
            RestObservable.loading(activity, isDialogShow)
            service.shopProductDetail(hashMap)
                .enqueue(object : Callback<ShopProductDetailResponse> {
                    override fun onResponse(
                        call: Call<ShopProductDetailResponse>,
                        response: Response<ShopProductDetailResponse>
                    ) {
                        if (response.isSuccessful) {
                            mResponse.value = RestObservable.success(response.body()!!)
                        } else {
                            mResponse.value = RestObservable.errorWithSuccess(
                                activity,
                                response.code(),
                                response.errorBody()!!
                            )
                            Log.e("error",response.errorBody().toString())
                        }
                    }

                    override fun onFailure(call: Call<ShopProductDetailResponse>, t: Throwable) {
                        Log.e("error",t.message.toString())
                        mResponse.value = RestObservable.error(activity, t)

                    }

                })
        } else {
            AppUtils.showMsgOnlyWithClick(activity,
                activity.getString(R.string.no_internet_connection), object : OnPopupClick {
                    override fun onPopupClickListener() {
                        shopProductDetailApi(activity, isDialogShow, hashMap)
                    }
                })
        }
    }


    fun addToCart(activity: Activity, isDialogShow: Boolean, hashMap: HashMap<String, String>) {
        if (activity.checkIfHasNetwork()) {
            RestObservable.loading(activity, isDialogShow)
            service.addCartItems(hashMap)
                .enqueue(object : Callback<CommonResponse> {
                    override fun onResponse(
                        call: Call<CommonResponse>,
                        response: Response<CommonResponse>
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

                    override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
                        mResponse.value = RestObservable.error(activity, t)
                    }

                })
        } else {
            AppUtils.showMsgOnlyWithClick(activity,
                activity.getString(R.string.no_internet_connection), object : OnPopupClick {
                    override fun onPopupClickListener() {
                        addToCart(activity, isDialogShow, hashMap)
                    }
                })
        }
    }


    fun cartListing(activity: Activity, isDialogShow: Boolean) {
        if (activity.checkIfHasNetwork()) {
            RestObservable.loading(activity, isDialogShow)
            service.cartItemListing()
                .enqueue(object : Callback<CartResponsModel> {
                    override fun onResponse(
                        call: Call<CartResponsModel>,
                        response: Response<CartResponsModel>
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

                    override fun onFailure(call: Call<CartResponsModel>, t: Throwable) {
                        mResponse.value = RestObservable.error(activity, t)
                    }

                })
        } else {
            AppUtils.showMsgOnlyWithClick(activity,
                activity.getString(R.string.no_internet_connection), object : OnPopupClick {
                    override fun onPopupClickListener() {
                        cartListing(activity, isDialogShow)
                    }
                })
        }
    }


    fun deleteCartItem(activity: Activity, isDialogShow: Boolean, id: String) {
        if (activity.checkIfHasNetwork()) {
            RestObservable.loading(activity, isDialogShow)
            service.deleteCartItem(id)
                .enqueue(object : Callback<CommonResponse> {
                    override fun onResponse(
                        call: Call<CommonResponse>,
                        response: Response<CommonResponse>
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

                    override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
                        mResponse.value = RestObservable.error(activity, t)
                    }

                })
        } else {
            AppUtils.showMsgOnlyWithClick(activity,
                activity.getString(R.string.no_internet_connection), object : OnPopupClick {
                    override fun onPopupClickListener() {
                        deleteCartItem(activity, isDialogShow, id)
                    }
                })
        }
    }


    fun faqListApi(activity: Activity, isDialogShow: Boolean) {
        if (activity.checkIfHasNetwork()) {
            RestObservable.loading(activity, isDialogShow)
            service.faq()
                .enqueue(object : Callback<FaqListResponse> {
                    override fun onResponse(
                        call: Call<FaqListResponse>,
                        response: Response<FaqListResponse>
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

                    override fun onFailure(call: Call<FaqListResponse>, t: Throwable) {
                        mResponse.value = RestObservable.error(activity, t)
                    }

                })
        } else {
            AppUtils.showMsgOnlyWithClick(activity,
                activity.getString(R.string.no_internet_connection), object : OnPopupClick {
                    override fun onPopupClickListener() {
                        faqListApi(activity, isDialogShow)
                    }
                })
        }
    }


    fun editShopProductApi(
        activity: Activity,
        isDialogShow: Boolean,
        hashMap: HashMap<String, RequestBody>,
    ) {
        if (activity.checkIfHasNetwork()) {
            Log.e("edit","entered")
            RestObservable.loading(activity, isDialogShow)
            service.editShopProduct(hashMap)
                .enqueue(object : Callback<AddProductResponse> {
                    override fun onResponse(
                        call: Call<AddProductResponse>,
                        response: Response<AddProductResponse>
                    ) {
                        if (response.isSuccessful) {
                            mResponse.value = RestObservable.success(response.body()!!)
                        } else {
                            Log.e("edit",response.errorBody().toString())
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
                        editShopProductApi(activity, isDialogShow, hashMap)
                    }
                })
        }
    }
    //New function for productGroup
    fun editProductGroup(
        activity: Activity,
        isDialogShow: Boolean,
        hashMap: HashMap<String, RequestBody>,
        images: ArrayList<MultipartBody.Part>?,
        mainImage: MultipartBody.Part?
    ){
        if (activity.checkIfHasNetwork()) {
            RestObservable.loading(activity, isDialogShow)
            service.editProductGroup(hashMap,images,mainImage)
                .enqueue(object : Callback<EditProductGroupResponse> {
                    override fun onResponse(
                        call: Call<EditProductGroupResponse>,
                        response: Response<EditProductGroupResponse>
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

                    override fun onFailure(call: Call<EditProductGroupResponse>, t: Throwable) {
                        mResponse.value = RestObservable.error(activity, t)
                    }
                })
        } else {
            AppUtils.showMsgOnlyWithClick(activity,
                activity.getString(R.string.no_internet_connection), object : OnPopupClick {
                    override fun onPopupClickListener() {
                        editProductGroup(activity, isDialogShow, hashMap,images,mainImage)
                    }
                })
        }
    }
    fun addProductGroup(
        activity: Activity,
        isDialogShow: Boolean,
        hashMap: HashMap<String, RequestBody>,
        images: ArrayList<MultipartBody.Part>,
        mainImage: MultipartBody.Part
    ){
        if (activity.checkIfHasNetwork()) {
            RestObservable.loading(activity, isDialogShow)
            service.addProductGroup(hashMap,images,mainImage)
                .enqueue(object : Callback<AddProductGroupResponse> {
                    override fun onResponse(
                        call: Call<AddProductGroupResponse>,
                        response: Response<AddProductGroupResponse>
                    ) {
                        if (response.isSuccessful) {
                            mResponse.value = RestObservable.success(response.body()!!)
                        } else {
//                            Log.e("group",response.raw().body.toString())
                            mResponse.value = RestObservable.errorWithSuccess(
                                activity,
                                response.code(),
                                response.errorBody()!!
                            )

                        }

                    }

                    override fun onFailure(call: Call<AddProductGroupResponse>, t: Throwable) {
                        mResponse.value = RestObservable.error(activity, t)
                    }

                })
        } else {
            AppUtils.showMsgOnlyWithClick(activity,
                activity.getString(R.string.no_internet_connection), object : OnPopupClick {
                    override fun onPopupClickListener() {
                        addProductGroup(activity, isDialogShow, hashMap,images,mainImage)
                    }
                })
        }
    }
    // New Function for product
    fun addProductVariant(
        activity: Activity,
        isDialogShow: Boolean,
        hashMap: HashMap<String, String>,
    ) {
        if (activity.checkIfHasNetwork()) {
            RestObservable.loading(activity, isDialogShow)
            service.addProductVariant(hashMap)
                .enqueue(object : Callback<AddProductVariantResponse> {
                    override fun onResponse(
                        call: Call<AddProductVariantResponse>,
                        response: Response<AddProductVariantResponse>
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

                    override fun onFailure(call: Call<AddProductVariantResponse>, t: Throwable) {
                        mResponse.value = RestObservable.error(activity, t)
                    }

                })
        } else {
            AppUtils.showMsgOnlyWithClick(activity,
                activity.getString(R.string.no_internet_connection), object : OnPopupClick {
                    override fun onPopupClickListener() {
                        addProductVariant(activity, isDialogShow, hashMap)
                    }
                })
        }
    }
    fun editProductVariant(
        activity: Activity,
        isDialogShow: Boolean,
        hashMap: HashMap<String, String>,
    ) {
        if (activity.checkIfHasNetwork()) {
            RestObservable.loading(activity, isDialogShow)
            service.editProductVariant(hashMap)
                .enqueue(object : Callback<AddProductVariantResponse> {
                    override fun onResponse(
                        call: Call<AddProductVariantResponse>,
                        response: Response<AddProductVariantResponse>
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

                    override fun onFailure(call: Call<AddProductVariantResponse>, t: Throwable) {
                        mResponse.value = RestObservable.error(activity, t)
                    }

                })
        } else {
            AppUtils.showMsgOnlyWithClick(activity,
                activity.getString(R.string.no_internet_connection), object : OnPopupClick {
                    override fun onPopupClickListener() {
                        editProductVariant(activity, isDialogShow, hashMap)
                    }
                })
        }
    }
    ///
    fun getProductReviews(
        activity: Activity,
        isDialogShow: Boolean,
        hashMap: HashMap<String, RequestBody>,
    ) {
        if (activity.checkIfHasNetwork()) {
            RestObservable.loading(activity, isDialogShow)
            service.getProductReviews(hashMap)
                .enqueue(object : Callback<ReviewsResponse> {
                    override fun onResponse(
                        call: Call<ReviewsResponse>,
                        response: Response<ReviewsResponse>
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

                    override fun onFailure(call: Call<ReviewsResponse>, t: Throwable) {
                        mResponse.value = RestObservable.error(activity, t)
                    }

                })
        } else {
            AppUtils.showMsgOnlyWithClick(activity,
                activity.getString(R.string.no_internet_connection), object : OnPopupClick {
                    override fun onPopupClickListener() {
                        getProductReviews(activity, isDialogShow, hashMap)
                    }
                })
        }
    }
    fun editShopDetail(
        activity: Activity,
        isDialogShow: Boolean,
        hashMap: HashMap<String, RequestBody>,
        image: MultipartBody.Part
    ) {
        if (activity.checkIfHasNetwork()) {
            Log.e("image",image.body.toString())
            RestObservable.loading(activity, isDialogShow)
            service.editShopDetail(hashMap, image)
                .enqueue(object : Callback<EditShopDeatilResponse> {
                    override fun onResponse(
                        call: Call<EditShopDeatilResponse>,
                        response: Response<EditShopDeatilResponse>
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

                    override fun onFailure(call: Call<EditShopDeatilResponse>, t: Throwable) {
                        mResponse.value = RestObservable.error(activity, t)
                    }

                })
        } else {
            AppUtils.showMsgOnlyWithClick(activity,
                activity.getString(R.string.no_internet_connection), object : OnPopupClick {
                    override fun onPopupClickListener() {
                        editShopDetail(activity, isDialogShow, hashMap, image)
                    }
                })
        }
    }


    fun salesListApi(activity: Activity, isDialogShow: Boolean, hashMap: HashMap<String, String>) {
        if (activity.checkIfHasNetwork()) {
            RestObservable.loading(activity, isDialogShow)
            service.sales(hashMap)
                .enqueue(object : Callback<SalesResponse> {
                    override fun onResponse(
                        call: Call<SalesResponse>,
                        response: Response<SalesResponse>
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

                    override fun onFailure(call: Call<SalesResponse>, t: Throwable) {
                        mResponse.value = RestObservable.error(activity, t)
                    }

                })
        } else {
            AppUtils.showMsgOnlyWithClick(activity,
                activity.getString(R.string.no_internet_connection), object : OnPopupClick {
                    override fun onPopupClickListener() {
                        salesListApi(activity, isDialogShow, hashMap)
                    }
                })
        }
    }

    fun cartUpdateApi(activity: Activity, isDialogShow: Boolean, hashMap: HashMap<String, String>) {
        if (activity.checkIfHasNetwork()) {
            RestObservable.loading(activity, isDialogShow)
            service.cartUpdate(hashMap)
                .enqueue(object : Callback<CartUpdateResponse> {
                    override fun onResponse(
                        call: Call<CartUpdateResponse>,
                        response: Response<CartUpdateResponse>
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

                    override fun onFailure(call: Call<CartUpdateResponse>, t: Throwable) {
                        mResponse.value = RestObservable.error(activity, t)
                    }

                })
        } else {
            AppUtils.showMsgOnlyWithClick(activity,
                activity.getString(R.string.no_internet_connection), object : OnPopupClick {
                    override fun onPopupClickListener() {
                        cartUpdateApi(activity, isDialogShow, hashMap)
                    }
                })
        }
    }

    fun addOrderApi(activity: Activity, isDialogShow: Boolean, hashMap: HashMap<String, String>) {
        if (activity.checkIfHasNetwork()) {
            RestObservable.loading(activity, isDialogShow)
            service.addOrder(hashMap)
                .enqueue(object : Callback<AddOrderResponse> {
                    override fun onResponse(
                        call: Call<AddOrderResponse>,
                        response: Response<AddOrderResponse>
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

                    override fun onFailure(call: Call<AddOrderResponse>, t: Throwable) {
                        mResponse.value = RestObservable.error(activity, t)
                    }

                })
        } else {
            AppUtils.showMsgOnlyWithClick(activity,
                activity.getString(R.string.no_internet_connection), object : OnPopupClick {
                    override fun onPopupClickListener() {
                        cartUpdateApi(activity, isDialogShow, hashMap)
                    }
                })
        }
    }


    /**
     * my orders api on user side
     */

    fun orderListingApi(
        activity: Activity,
        hashMap: HashMap<String, String>,
        isDialogShow: Boolean
    ) {
        if (activity.checkIfHasNetwork()) {
            RestObservable.loading(activity, isDialogShow)
            service.orderListingApi(hashMap)
                .enqueue(object : Callback<UserOrderListing> {
                    override fun onResponse(
                        call: Call<UserOrderListing>,
                        response: Response<UserOrderListing>
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

                    override fun onFailure(call: Call<UserOrderListing>, t: Throwable) {
                        mResponse.value = RestObservable.error(activity, t)
                    }

                })
        } else {
            AppUtils.showMsgOnlyWithClick(activity,
                activity.getString(R.string.no_internet_connection), object : OnPopupClick {
                    override fun onPopupClickListener() {
                        orderListingApi(activity, hashMap, isDialogShow)
                    }
                })
        }
    }


    /**
     * update order status api call
     */

    fun orderStatusApi(
        activity: Activity,
        hashMap: HashMap<String, String>,
        isDialogShow: Boolean
    ) {
        if (activity.checkIfHasNetwork()) {
            RestObservable.loading(activity, isDialogShow)
            service.orderStatusApi(hashMap)
                .enqueue(object : Callback<ScanOrderResponse> {
                    override fun onResponse(
                        call: Call<ScanOrderResponse>,
                        response: Response<ScanOrderResponse>
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

                    override fun onFailure(call: Call<ScanOrderResponse>, t: Throwable) {
                        mResponse.value = RestObservable.error(activity, t)
                    }

                })
        } else {
            AppUtils.showMsgOnlyWithClick(activity,
                activity.getString(R.string.no_internet_connection), object : OnPopupClick {
                    override fun onPopupClickListener() {
                        orderStatusApi(activity, hashMap, isDialogShow)
                    }
                })
        }


    }

    fun cancelOrderApi(
        activity: Activity,
        hashMap: HashMap<String, String>,
        isDialogShow: Boolean
    ) {
        if (activity.checkIfHasNetwork()) {
            RestObservable.loading(activity, isDialogShow)
            service.cancelOrderApi(hashMap)
                .enqueue(object : Callback<CommonResponse> {
                    override fun onResponse(
                        call: Call<CommonResponse>,
                        response: Response<CommonResponse>
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

                    override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
                        mResponse.value = RestObservable.error(activity, t)
                    }

                })
        } else {
            AppUtils.showMsgOnlyWithClick(activity,
                activity.getString(R.string.no_internet_connection), object : OnPopupClick {
                    override fun onPopupClickListener() {
                        cancelOrderApi(activity, hashMap, isDialogShow)
                    }
                })
        }


    }

    fun readNotificationApi(
        activity: Activity,
        hashMap: HashMap<String, String>,
        isDialogShow: Boolean
    ) {
        if (activity.checkIfHasNetwork()) {
            RestObservable.loading(activity, isDialogShow)
            service.notificationRead(hashMap)
                .enqueue(object : Callback<CommonResponse> {
                    override fun onResponse(
                        call: Call<CommonResponse>,
                        response: Response<CommonResponse>
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

                    override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
                        mResponse.value = RestObservable.error(activity, t)
                    }

                })
        } else {
            AppUtils.showMsgOnlyWithClick(activity,
                activity.getString(R.string.no_internet_connection), object : OnPopupClick {
                    override fun onPopupClickListener() {
                        readNotificationApi(activity, hashMap, isDialogShow)
                    }
                })
        }


    }


    fun getBankApi(
        activity: Activity,
        isDialogShow: Boolean
    ) {
        if (activity.checkIfHasNetwork()) {
            RestObservable.loading(activity, isDialogShow)
            service.getBankAccount()
                .enqueue(object : Callback<GetBankResponse> {
                    override fun onResponse(
                        call: Call<GetBankResponse>,
                        response: Response<GetBankResponse>
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

                    override fun onFailure(call: Call<GetBankResponse>, t: Throwable) {
                        mResponse.value = RestObservable.error(activity, t)
                    }

                })
        } else {
            AppUtils.showMsgOnlyWithClick(activity,
                activity.getString(R.string.no_internet_connection), object : OnPopupClick {
                    override fun onPopupClickListener() {
                        getBankApi(activity, isDialogShow)
                    }
                })
        }


    }

    fun deleteBank(
        activity: Activity,
        hashMap: HashMap<String, String>,
        isDialogShow: Boolean
    ) {
        if (activity.checkIfHasNetwork()) {
            RestObservable.loading(activity, isDialogShow)
            service.deleteBank(hashMap)
                .enqueue(object : Callback<DeleteBankResponse> {
                    override fun onResponse(
                        call: Call<DeleteBankResponse>,
                        response: Response<DeleteBankResponse>
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

                    override fun onFailure(call: Call<DeleteBankResponse>, t: Throwable) {
                        mResponse.value = RestObservable.error(activity, t)
                    }

                })
        } else {
            AppUtils.showMsgOnlyWithClick(activity,
                activity.getString(R.string.no_internet_connection), object : OnPopupClick {
                    override fun onPopupClickListener() {
                        deleteBank(activity, hashMap, isDialogShow)
                    }
                })
        }


    }

    fun deleteCard(
        activity: Activity,
        hashMap: HashMap<String, String>,
        isDialogShow: Boolean
    ) {
        if (activity.checkIfHasNetwork()) {
            RestObservable.loading(activity, isDialogShow)
            service.deleteCard(hashMap)
                .enqueue(object : Callback<CommonResponse> {
                    override fun onResponse(
                        call: Call<CommonResponse>,
                        response: Response<CommonResponse>
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

                    override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
                        mResponse.value = RestObservable.error(activity, t)
                    }

                })
        } else {
            AppUtils.showMsgOnlyWithClick(activity,
                activity.getString(R.string.no_internet_connection), object : OnPopupClick {
                    override fun onPopupClickListener() {
                        deleteCard(activity, hashMap, isDialogShow)
                    }
                })
        }


    }


    fun myEarning(
        activity: Activity,
        hashMap: HashMap<String, String>,
        isDialogShow: Boolean
    ) {
        if (activity.checkIfHasNetwork()) {
            RestObservable.loading(activity, isDialogShow)
            service.myEarningApi(hashMap)
                .enqueue(object : Callback<MyEarningResponse> {
                    override fun onResponse(
                        call: Call<MyEarningResponse>,
                        response: Response<MyEarningResponse>
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

                    override fun onFailure(call: Call<MyEarningResponse>, t: Throwable) {
                        mResponse.value = RestObservable.error(activity, t)
                    }

                })
        } else {
            AppUtils.showMsgOnlyWithClick(activity,
                activity.getString(R.string.no_internet_connection), object : OnPopupClick {
                    override fun onPopupClickListener() {
                        myEarning(activity, hashMap, isDialogShow)
                    }
                })
        }


    }

    fun transactionApi(
        activity: Activity,
        isDialogShow: Boolean
    ) {
        if (activity.checkIfHasNetwork()) {
            RestObservable.loading(activity, isDialogShow)
            service.transactionList()
                .enqueue(object : Callback<AllTransactionsResponse> {
                    override fun onResponse(
                        call: Call<AllTransactionsResponse>,
                        response: Response<AllTransactionsResponse>
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

                    override fun onFailure(call: Call<AllTransactionsResponse>, t: Throwable) {
                        mResponse.value = RestObservable.error(activity, t)
                    }

                })
        } else {
            AppUtils.showMsgOnlyWithClick(activity,
                activity.getString(R.string.no_internet_connection), object : OnPopupClick {
                    override fun onPopupClickListener() {
                        transactionApi(activity, isDialogShow)
                    }
                })
        }


    }


    fun withdrawRequestApi(
        activity: Activity,
        hashMap: HashMap<String, String>,
        isDialogShow: Boolean
    ) {
        if (activity.checkIfHasNetwork()) {
            RestObservable.loading(activity, isDialogShow)
            service.withdrawRequest(hashMap)
                .enqueue(object : Callback<WithdrawRequestResponse> {
                    override fun onResponse(
                        call: Call<WithdrawRequestResponse>,
                        response: Response<WithdrawRequestResponse>
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

                    override fun onFailure(call: Call<WithdrawRequestResponse>, t: Throwable) {
                        mResponse.value = RestObservable.error(activity, t)
                    }

                })
        } else {
            AppUtils.showMsgOnlyWithClick(activity,
                activity.getString(R.string.no_internet_connection), object : OnPopupClick {
                    override fun onPopupClickListener() {
                        withdrawRequestApi(activity, hashMap, isDialogShow)
                    }
                })
        }


    }

    fun searchProductApi(
        activity: Activity,
        hashMap: HashMap<String, String>,
        isDialogShow: Boolean
    ) {
        if (activity.checkIfHasNetwork()) {
            RestObservable.loading(activity, isDialogShow)
            service.searchProduct(hashMap)
                .enqueue(object : Callback<SearchProductResponse> {
                    override fun onResponse(
                        call: Call<SearchProductResponse>,
                        response: Response<SearchProductResponse>
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

                    override fun onFailure(call: Call<SearchProductResponse>, t: Throwable) {
                        mResponse.value = RestObservable.error(activity, t)
                    }

                })
        } else {
            AppUtils.showMsgOnlyWithClick(activity,
                activity.getString(R.string.no_internet_connection), object : OnPopupClick {
                    override fun onPopupClickListener() {
                        searchProductApi(activity, hashMap, isDialogShow)
                    }
                })
        }


    }

    fun filterProductApi(
        activity: Activity,
        hashMap: HashMap<String, String>,
        isDialogShow: Boolean
    ) {
        if (activity.checkIfHasNetwork()) {
            RestObservable.loading(activity, isDialogShow)
            service.filterProduct(hashMap)
                .enqueue(object : Callback<SearchProductResponse> {
                    override fun onResponse(
                        call: Call<SearchProductResponse>,
                        response: Response<SearchProductResponse>
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

                    override fun onFailure(call: Call<SearchProductResponse>, t: Throwable) {
                        mResponse.value = RestObservable.error(activity, t)
                    }

                })
        } else {
            AppUtils.showMsgOnlyWithClick(activity,
                activity.getString(R.string.no_internet_connection), object : OnPopupClick {
                    override fun onPopupClickListener() {
                        searchProductApi(activity, hashMap, isDialogShow)
                    }
                })
        }


    }
}