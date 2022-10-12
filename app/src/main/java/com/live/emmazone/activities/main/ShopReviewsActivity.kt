package com.live.emmazone.activities.main

import android.media.Rating
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.live.emmazone.R
import com.live.emmazone.databinding.ActivityShopReviewsBinding
import com.live.emmazone.extensionfuncton.Validator
import com.live.emmazone.extensionfuncton.getPreference
import com.live.emmazone.interfaces.OnPopupClick
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.RatingResponse
import com.live.emmazone.response_model.ShopDetailResponse
import com.live.emmazone.response_model.ShopListingResponse
import com.live.emmazone.response_model.WishListResponse
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.utils.AppUtils
import com.live.emmazone.view_models.AppViewModel
import com.schunts.extensionfuncton.loadImage

class ShopReviewsActivity : AppCompatActivity(),
    Observer<RestObservable>, OnPopupClick {

    private val appViewModel: AppViewModel by viewModels()
    private lateinit var binding: ActivityShopReviewsBinding
    private var shopId = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopReviewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        val shopDetails = intent.getParcelableExtra<ShopDetailResponse.Body>(AppConstants.SHOP_DETAIL_RESPONSE) as ShopDetailResponse.Body
        shopId = intent.getStringExtra("vendorId").toString()
        clicksHandle()
        getReview()
    }

    private fun setData(data : RatingResponse) {
        data.body?.ratings?.toFloatOrNull()?.let {
            binding.ratingbarShopReviews.rating = it
        }
        data.body?.comment?.let {
            binding.edtInsertCommentShopReview.setText(it)
        }
    }

    private fun clicksHandle() {
        binding.back.setOnClickListener {
            onBackPressed()
        }
        binding.btnSubmitShopReview.setOnClickListener {
            validateData()
        }
    }

    private fun validateData() {
        val rating = binding.ratingbarShopReviews.rating
        val comment = binding.edtInsertCommentShopReview.text.toString().trim()

        if (Validator.reviewValidation(rating, comment)) {

            val hashMap = HashMap<String, String>()
            hashMap["vendorId"] = shopId
            hashMap["ratings"] = rating.toString()
            hashMap["comment"] = comment

            appViewModel.ratingApi(this, true, hashMap)
            appViewModel.getResponse().observe(this, this)
        } else {
            AppUtils.showMsgOnlyWithoutClick(this, Validator.errorMessage)
        }
    }
    private fun getReview(){
        val hashMap = HashMap<String, String>()
        hashMap["vendorId"] = shopId

        appViewModel.getMyReviewShop(this, true, hashMap)
        appViewModel.getResponse().observe(this, this)
    }

    override fun onChanged(t: RestObservable?) {
        when (t!!.status) {
            Status.SUCCESS -> {
                if (t.data is RatingResponse) {
                    val response: RatingResponse = t.data
                    if (response.code == AppConstants.SUCCESS_CODE) {
                        if (response.message == "Ratings fetched successfully") {
                            setData(response)
                        } else {
                            AppUtils.showMsgOnlyWithClick(this, response.message, this)
                        }
                    }
                }
            }
            else -> {}
        }
    }

    override fun onPopupClickListener() {
        finish()
    }
}