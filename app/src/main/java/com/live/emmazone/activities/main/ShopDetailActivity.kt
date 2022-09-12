package com.live.emmazone.activities.main

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.live.emmazone.R
import com.live.emmazone.activities.ImageZoomActivity
import com.live.emmazone.activities.auth.LoginActivity
import com.live.emmazone.adapter.AdapterShopDetailCategory
import com.live.emmazone.adapter.AdapterShopDetailProducts
import com.live.emmazone.databinding.ActivityShopDetailBinding
import com.live.emmazone.extensionfuncton.getPreference
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.AddFavouriteResponse
import com.live.emmazone.response_model.SellerShopDetailResponse
import com.live.emmazone.response_model.ShopDetailResponse
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.utils.AppUtils.Companion.openGoogleMaps
import com.live.emmazone.utils.AppUtils.Companion.showToast
import com.live.emmazone.utils.LocationUpdateUtility
import com.live.emmazone.view_models.AppViewModel
import com.schunts.extensionfuncton.loadImage

class ShopDetailActivity : LocationUpdateUtility(), Observer<RestObservable> {

    private val appViewModel: AppViewModel by viewModels()
    private var response: ShopDetailResponse? = null


    lateinit var binding: ActivityShopDetailBinding
    private var listSDProduct = ArrayList<SellerShopDetailResponse.Body.ShopDetails.Product>()
    lateinit var adapter: AdapterShopDetailCategory

    override fun updatedLatLng(lat: Double?, lng: Double?) {
        if (lat != null && lng != null) {
            if (this.baseContext != null) {
                stopLocationUpdates()
                shopDetailApiHit(lat.toString(), lng.toString())
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clicksHandle()


        binding.recyclerShopDetailProducts.layoutManager = GridLayoutManager(this, 2)

    }

    private fun shopDetailApiHit(latitude: String, longitude: String) {
        val shopId = intent.getStringExtra(AppConstants.SHOP_ID)
        val hashMap = HashMap<String, String>()
        hashMap["shopId"] = shopId!!
        hashMap["latitude"] = latitude
        hashMap["longitude"] = longitude

        appViewModel.shopDetailApi(this, true, hashMap)
        appViewModel.getResponse().observe(this, this)
    }

    private fun clicksHandle() {
        binding.back.setOnClickListener {
            onBackPressed()
        }

        binding.imageCart.setOnClickListener {
            if (getPreference(AppConstants.PROFILE_TYPE, "") == AppConstants.GUEST) {
                showLoginDialog()
                return@setOnClickListener
            }
            val intent = Intent(this, Cart::class.java)
            startActivity(intent)
        }

        binding.imageAskExpert.setOnClickListener {
            if (getPreference(AppConstants.PROFILE_TYPE, "") == AppConstants.GUEST) {
                showLoginDialog()
                return@setOnClickListener
            }
            val intent = Intent(this, ChatActivity::class.java)
            intent.putExtra(AppConstants.USER2_NAME, response!!.body.shopName)
            intent.putExtra(
                AppConstants.USER2_IMAGE,
                AppConstants.IMAGE_USER_URL + response!!.body.image
            )
            intent.putExtra(AppConstants.USER2_ID, response!!.body.userId.toString())
            startActivity(intent)
        }

        binding.itemHeartShopDetail.setOnClickListener {
            if (getPreference(AppConstants.PROFILE_TYPE, "") == "guest") {
                showLoginDialog()
            } else {
                favUnFavApiHit()
            }
        }

        binding.imageShopDetail.setOnClickListener {
            val intent = Intent(this, ImageZoomActivity::class.java)
            intent.putExtra(
                AppConstants.IMAGE_USER_URL,
                AppConstants.IMAGE_USER_URL + response!!.body.image
            )
            startActivity(intent)
        }
        binding.tvShopAddress.setOnClickListener {
            response?.let {
                it.body.let { details->
                    try {
                        openGoogleMaps(details.latitude,details.longitude)
                    }
                    catch (e : Exception){
                        showToast("Google Maps is not installed in the device.")
                    }
                }
            }
        }

    }
    private fun showLoginDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(false)

        dialog.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT,
        )
        dialog.setContentView(R.layout.dialog_login)

        val imgCross = dialog.findViewById<ImageView>(R.id.cross)
        val btnLogin = dialog.findViewById<Button>(R.id.btnLogin)

        imgCross.setOnClickListener {
            dialog.dismiss()
        }

        btnLogin.setOnClickListener {
            dialog.dismiss()
            startActivity(Intent(this, LoginActivity::class.java))
        }

        dialog.show()

    }


    private fun favUnFavApiHit() {
        val hashMap = HashMap<String, String>()
        hashMap["vendorId"] = response!!.body.userId.toString()

        if (response!!.body.isLiked == 1)
            hashMap["status"] = "0"
        else {
            hashMap["status"] = "1"
        }

        appViewModel.addFavApi(this, true, hashMap)
        appViewModel.getResponse().observe(this, this)
    }

    override fun onChanged(t: RestObservable?) {
        when (t!!.status) {
            Status.SUCCESS -> {
                if (t.data is ShopDetailResponse) {
                    response = t.data

                    if (response!!.code == AppConstants.SUCCESS_CODE) {
                        setData()
                        setCategoryAdapter()
                    }
                } else if (t.data is AddFavouriteResponse) {
                    val response: AddFavouriteResponse = t.data

                    if (response.code == AppConstants.SUCCESS_CODE) {

                        if (response.body.status == 1) {
                            binding.itemHeartShopDetail.setImageResource(R.drawable.heart)
                        } else {
                            binding.itemHeartShopDetail.setImageResource(R.drawable.heart_unselect)
                        }
                    }

                }
            }
            else -> {}
        }
    }

    private fun setCategoryAdapter() {
        val shopCategory = AdapterShopDetailCategory(response!!.body.shopCategories)
        binding.recyclerShopDetailCategory.adapter = shopCategory
    }


    private fun setData() {
        binding.imageShopDetail.loadImage(AppConstants.IMAGE_USER_URL + response!!.body.image)
        binding.tvWishListStoreName.text = response!!.body.shopName
        binding.tvDesc.text = response!!.body.shopDescription
        binding.tvShopFY.text = getString(R.string.since, response!!.body.year.toString())
        binding.tvShopAddress.text = response!!.body.shopAddress
        binding.tvWishListRatingText.text = response!!.body.ratings.toString() + "/" + "5"
        val distance = intent.getIntExtra("distance",0)
        binding.tvWishListDistance.text = distance.toString() + " " + getString(R.string.miles_away)

        if (response!!.body.ratings.isNotEmpty()) {
            binding.ratingBarWishList.rating = response!!.body.ratings.toFloat()
        }
        if (response!!.body.isLiked == 1) {
            binding.itemHeartShopDetail.setImageResource(R.drawable.heart)
        } else {
            binding.itemHeartShopDetail.setImageResource(R.drawable.heart_unselect)
        }
        if (response!!.body.products.isNotEmpty()) {
            listSDProduct = response!!.body.products

            val productAdapter = AdapterShopDetailProducts(this, listSDProduct)
            binding.recyclerShopDetailProducts.adapter = productAdapter

            productAdapter.onItemClick = { productId: String ->
                val intent = Intent(this, ProductDetailActivity::class.java)
                intent.putExtra(AppConstants.USER2_NAME, response!!.body.shopName)
                intent.putExtra(
                    AppConstants.USER2_IMAGE,
                    AppConstants.IMAGE_USER_URL + response!!.body.image
                )
                intent.putExtra("productId", productId)
                startActivity(intent)
            }

        } else {
            binding.tvNoProduct.visibility = View.VISIBLE
        }

        if (response!!.body.cartCount == 0) {
            binding.ivRedCart.visibility = View.GONE
        } else {
            binding.ivRedCart.visibility = View.VISIBLE
        }

        binding.nestedScroll.visibility = View.VISIBLE
        binding.tvNoData.visibility = View.GONE

    }


    override fun onResume() {
        super.onResume()
        getLiveLocation(this)
    }

    override fun onStop() {
        super.onStop()
        stopLocationUpdates()
    }
}