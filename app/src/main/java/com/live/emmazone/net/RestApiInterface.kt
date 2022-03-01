package com.live.emmazone.net


import com.google.android.gms.common.internal.service.Common
import com.live.emmazone.model.CartResponsModel
import com.live.emmazone.model.ShopProductDetailResponse
import com.live.emmazone.model.sellerShopDetails.SellerShopDetailsResponse
import com.live.emmazone.response_model.*
import com.live.emmazone.utils.AppConstants
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface RestApiInterface {

    @Multipart
    @POST(AppConstants.SIGN_UP)
    fun signUp(
        @PartMap hashMap: HashMap<String, RequestBody>,
        @Part image: MultipartBody.Part?
    ): Call<SignUpResponse>

    @FormUrlEncoded
    @POST(AppConstants.OTP_VERIFY)
    fun otpVerify(
        @FieldMap hashMap: HashMap<String, String>
    ): Call<OtpVerifyResponse>


    @POST(AppConstants.RESEND_OTP)
    fun resendOtp(
    ): Call<OtpResendResponse>


    @FormUrlEncoded
    @POST(AppConstants.LOGIN)
    fun login(
        @FieldMap hashMap: HashMap<String, String>
    ): Call<LoginResponse>

    @GET(AppConstants.TERMS)
    fun termsCondition(): Call<TermsConditionResponse>

    @GET(AppConstants.PRIVACY_POLICY)
    fun privacyPolicy(): Call<PrivacyPolicyResponse>

    @FormUrlEncoded
    @POST(AppConstants.FORGOT_PASSWORD)
    fun forgotPassword(@FieldMap hashMap: HashMap<String, String>): Call<ForgotPasswordResponse>


    @GET(AppConstants.CATEGORY_LIST)
    fun categoryList(): Call<CategoryListResponse>

    @GET(AppConstants.SELLER_SHOP_DETAILS)
    fun sellerShopDetails(): Call<SellerShopDetailsResponse>

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = AppConstants.DELETE_PRODUCT, hasBody = true)
    fun deleteProduct(
        @Field("product_id") product_id: String
    ): Call<CommonResponse>

    @Multipart
    @POST(AppConstants.ADD_SHOP)
    fun addShop(
        @PartMap hashMap: HashMap<String, RequestBody>,
        @Part image: MultipartBody.Part
    ): Call<AddShopResponse>

    @FormUrlEncoded
    @POST(AppConstants.CHANGE_PASSWORD)
    fun changePassword(@FieldMap hashMap: HashMap<String, String>): Call<ChangePasswordResponse>

    @GET(AppConstants.NOTIFICATION_LIST)
    fun notificationList(): Call<NotificatioListingResponse>

    @FormUrlEncoded
    @POST(AppConstants.NOTIFICATION_STATUS)
    fun notificationStatus(@FieldMap hashMap: HashMap<String, String>): Call<NotificationStatusResponse>

    @GET(AppConstants.PROFILE)
    fun profile(): Call<ProfileResponse>

    @Multipart
    @POST(AppConstants.EDIT_PROFILE)
    fun editProfile(
        @PartMap hashMap: HashMap<String, RequestBody>,
        @Part image: MultipartBody.Part
    ): Call<EditProfileResponse>

    @FormUrlEncoded
    @POST(AppConstants.ADD_NEW_ADDRESS)
    fun addAddress(@FieldMap hashMap: HashMap<String, String>): Call<AddNewAddressResponse>

    @GET(AppConstants.ADDRESS_LIST)
    fun addressList(): Call<AddressListResponse>

    @FormUrlEncoded
    @POST(AppConstants.SHOP_LIST)
    fun shopListing(@FieldMap hashMap: HashMap<String, String>): Call<ShopListingResponse>

    @FormUrlEncoded
    @POST(AppConstants.FAV_SHOP)
    fun favShop(@FieldMap hashMap: HashMap<String, String>): Call<AddFavouriteResponse>

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = AppConstants.DELETE_ADDRESS, hasBody = true)
    fun deleteAddress(@FieldMap hashMap: HashMap<String, String>): Call<DeleteAddressResponse>

    @Multipart
    @POST(AppConstants.ADD_SHOP_PRODUCT)
    fun addProduct(
        @PartMap hashMap: HashMap<String, RequestBody>,
        @Part images: ArrayList<MultipartBody.Part>,
        @Part mainImage: MultipartBody.Part
    ): Call<AddProductResponse>


    @GET(AppConstants.GET_SELECTED_CATEGORY)
    fun selectedCategoryList(): Call<CategoryListResponse>

    @POST(AppConstants.LOGOUT)
    fun logout(): Call<LogoutResponse>

    @FormUrlEncoded
    @POST(AppConstants.RATING)
    fun rating(@FieldMap hashMap: HashMap<String, String>): Call<RatingResponse>

    @FormUrlEncoded
    @POST(AppConstants.CATEGORY_COLOR_SIZE)
    fun categoryColorSize(@FieldMap hashMap: HashMap<String, String>): Call<CategoryColorSizeResponse>

    @FormUrlEncoded
    @POST(AppConstants.ADD_CARD)
    fun addCard(@FieldMap hashMap: HashMap<String, String>): Call<AddCardResponse>

    @GET(AppConstants.CARD_LIST)
    fun cardList(): Call<CardListResponse>

    @FormUrlEncoded
    @POST(AppConstants.ADD_ACCOUNT)
    fun addAccount(@FieldMap hashMap: HashMap<String, String>): Call<AddBankResponse>

    @GET(AppConstants.WISH_LIST)
    fun wishList(): Call<WishListResponse>

    @FormUrlEncoded
    @POST(AppConstants.SHOP_DETAIL)
    fun shopDetail(@FieldMap hashMap: HashMap<String, String>): Call<ShopDetailResponse>

    @GET(AppConstants.FAQ)
    fun faq(): Call<FaqListResponse>


    @Multipart
    @PUT(AppConstants.EDIT_SHOP_PRODUCT)
    fun editShopProduct(
        @PartMap hashMap: HashMap<String, RequestBody>,
        @Part images: ArrayList<MultipartBody.Part>?,
        @Part mainImage: MultipartBody.Part?
    ): Call<AddProductResponse>


    @FormUrlEncoded
    @POST(AppConstants.PRODUCT_DETAIl)
    fun shopProductDetail(@FieldMap hashMap: HashMap<String, String>): Call<ShopProductDetailResponse>


    @FormUrlEncoded
    @POST(AppConstants.ADD_CART_ITEMS)
    fun addCartItems(@FieldMap hashMap: HashMap<String, String>): Call<CommonResponse>


    @GET(AppConstants.CART_LISTING)
    fun cartItemListing(): Call<CartResponsModel>

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = AppConstants.DELETE_CART_ITEM, hasBody = true)
    fun deleteCartItem(
        @Field("id") id: String
    ): Call<CommonResponse>

}