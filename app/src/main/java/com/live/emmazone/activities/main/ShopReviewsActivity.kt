package com.live.emmazone.activities.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.live.emmazone.R
import com.live.emmazone.databinding.ActivityShopReviewsBinding
import com.live.emmazone.extensionfuncton.Validator
import com.live.emmazone.interfaces.OnPopupClick
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.RatingResponse
import com.live.emmazone.response_model.ShopListingResponse
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.utils.AppUtils
import com.live.emmazone.view_models.AppViewModel
import com.schunts.extensionfuncton.loadImage

class ShopReviewsActivity : AppCompatActivity(),
    Observer<RestObservable>, OnPopupClick {

    private val appViewModel: AppViewModel by viewModels()
    private lateinit var binding: ActivityShopReviewsBinding
    private var shopResponse: ShopListingResponse.Body? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopReviewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.getSerializableExtra(AppConstants.SHOP_LISTING_RESPONSE) != null) {
            shopResponse = intent.getSerializableExtra(AppConstants.SHOP_LISTING_RESPONSE)
                    as ShopListingResponse.Body

            binding.imageShopDetail.loadImage(AppConstants.SHOP_IMAGE_URL+shopResponse!!.image)
            binding.tvWishListStoreName.text = shopResponse!!.shopName
            binding.tvWishListRatingText.text = shopResponse!!.ratings + "/" + "5"

            if (shopResponse!!.ratings.isNotEmpty()) {
                binding.ratingBarWishList.rating = shopResponse!!.ratings.toFloat()
            }
        }

        clicksHandle()

    }

    private fun clicksHandle() {
        binding.ratingBarWishList.setIsIndicator(false)

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
            hashMap["vendorId"] = shopResponse!!.id.toString()
            hashMap["ratings"] = rating.toString()
            hashMap["comment"] = comment

            appViewModel.ratingApi(this, true, hashMap)
            appViewModel.getResponse().observe(this, this)
        } else {
            AppUtils.showMsgOnlyWithoutClick(this, Validator.errorMessage)
        }
    }

    override fun onChanged(t: RestObservable?) {
        when (t!!.status) {
            Status.SUCCESS -> {
                if (t.data is RatingResponse) {
                    val response: RatingResponse = t.data

                    if (response.code == AppConstants.SUCCESS_CODE) {
                        AppUtils.showMsgOnlyWithClick(this, response.message, this)
                    }
                }
            }
        }
    }

    override fun onPopupClickListener() {
        finish()
    }
}