package com.live.emmazone.activities.main

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.live.emmazone.R
import com.live.emmazone.activities.auth.LoginActivity
import com.live.emmazone.activities.listeners.OnItemClick
import com.live.emmazone.adapter.AdapterShopDetailCategory
import com.live.emmazone.adapter.AdapterShopDetailProducts
import com.live.emmazone.databinding.ActivityShopDetailBinding
import com.live.emmazone.extensionfuncton.getPreference
import com.live.emmazone.model.ModelShopDetailCategory
import com.live.emmazone.model.ModelShopDetailProducts
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.AddFavouriteResponse
import com.live.emmazone.response_model.SellerShopDetailResponse
import com.live.emmazone.response_model.ShopDetailResponse
import com.live.emmazone.response_model.ShopListingResponse
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.utils.AppUtils
import com.live.emmazone.view_models.AppViewModel
import com.schunts.extensionfuncton.loadImage

class ShopDetailActivity : AppCompatActivity(), OnItemClick, Observer<RestObservable> {

    private val appViewModel: AppViewModel by viewModels()
    private var response: ShopDetailResponse? = null


    lateinit var binding: ActivityShopDetailBinding
    var listSDProduct = ArrayList<SellerShopDetailResponse.Body.ShopDetails.Product>()
    lateinit var adapter: AdapterShopDetailCategory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clicksHandle()


        binding.recyclerShopDetailProducts.layoutManager = GridLayoutManager(this, 2)

    }

    private fun shopDetailApiHit() {
        val shopId = intent.getStringExtra(AppConstants.SHOP_ID)

        val hashMap = HashMap<String, String>()
        hashMap["shopId"] = shopId!!

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
            val intent = Intent(this, Message::class.java)
            startActivity(intent)
        }

        binding.itemHeartShopDetail.setOnClickListener {
            favUnFavApiHit()
        }

    }

    override fun onCellClickListener() {
        val intent = Intent(this, ProductDetailActivity::class.java)
        startActivity(intent)
    }

    override fun onClick() {

    }

    override fun onClickPickCollect() {

    }

    override fun onOrderCancelled() {

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
        binding.tvWishListDistance.text = response!!.body.distance.toString() + " " +
                getString(R.string.miles_away)

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
            binding.recyclerShopDetailProducts.adapter =
                AdapterShopDetailProducts(this, listSDProduct, this)

        } else {
            binding.tvNoProduct.visibility = View.VISIBLE
        }

        if (response!!.body.cartCount == 0) {
            binding.ivRedCart.visibility = View.GONE
        } else {
            binding.ivRedCart.visibility = View.VISIBLE
        }

    }


    override fun onResume() {
        super.onResume()
        shopDetailApiHit()
    }
}