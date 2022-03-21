package com.live.emmazone.activities.fragment

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.live.emmazone.MainActivity
import com.live.emmazone.R
import com.live.emmazone.activities.auth.LoginActivity
import com.live.emmazone.activities.auth.UserLoginChoice
import com.live.emmazone.activities.main.*
import com.live.emmazone.adapter.AdapterNearbyShops
import com.live.emmazone.databinding.FragmentHomeBinding
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.extensionfuncton.getPreference
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.AddFavouriteResponse
import com.live.emmazone.response_model.ShopListingResponse
import com.live.emmazone.utils.AppUtils
import com.live.emmazone.utils.LocationUpdateUtilityFragment
import com.live.emmazone.view_models.AppViewModel

class HomeFragment : LocationUpdateUtilityFragment(), Observer<RestObservable> {

    private val appViewModel: AppViewModel by viewModels()

    var mLatitude = ""
    var mLongitude = ""

    private lateinit var binding: FragmentHomeBinding

    private val list = ArrayList<ShopListingResponse.Body.Shop>()
    lateinit var nearShopAdapter: AdapterNearbyShops
    private var selectedPos: Int? = null

    override fun updatedLatLng(lat: Double?, lng: Double?) {
        if (lat != null && lng != null) {
            mLatitude = lat.toString()
            mLongitude = lng.toString()

            shopListingApi()
            stopLocationUpdates()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        clicksHandle()
        getLiveLocation(requireActivity())


    }

    private fun clicksHandle() {
        if (getPreference(AppConstants.PROFILE_TYPE, "") == "guest") {
            binding.tvUserName.text = "Guest"
        } else {
            binding.tvUserName.text = getPreference(AppConstants.NAME, "").toString()
        }

        binding.btnClickHereHome.setOnClickListener {
            if (getPreference(AppConstants.PROFILE_TYPE, "") == "guest") {
                startActivity(Intent(activity, UserLoginChoice::class.java))
            } else {
                startActivity(
                    Intent(
                        activity,
                        LoginActivity::class.java
                    ).putExtra(AppConstants.USER_CHOICE, "3")
                )
            }
        }

        binding.cart.setOnClickListener {
            if (getPreference(AppConstants.PROFILE_TYPE, "") == AppConstants.GUEST) {
                (activity as MainActivity).showLoginDialog()
                return@setOnClickListener
            }
            val intent = Intent(activity, Cart::class.java)
            startActivity(intent)
        }

        binding.imageFilterHome.setOnClickListener {
            val intent = Intent(activity, FilterActivity::class.java)
            startActivity(intent)
        }

        binding.imageNotifications.setOnClickListener {
            if (getPreference(AppConstants.PROFILE_TYPE, "") == AppConstants.GUEST) {
                (activity as MainActivity).showLoginDialog()
                return@setOnClickListener
            }
            val intent = Intent(activity, Notifications::class.java)
            startActivity(intent)
        }

        binding.imageLocationHome.setOnClickListener {
            locationEnableDialog()
        }
    }

    private fun locationEnableDialog() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.activity_enable_location_services)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val crossIcon = dialog.findViewById<ImageView>(R.id.crossImage)
        val btnEnable = dialog.findViewById<Button>(R.id.button_enable_services)

        crossIcon.setOnClickListener {
            dialog.dismiss()
        }

        btnEnable.setOnClickListener {
            dialog.dismiss()
        }

        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window!!.setGravity(Gravity.CENTER)
        dialog.show()
    }

    private fun setShopAdapter() {
        nearShopAdapter = AdapterNearbyShops(list)
        binding.rvHomeNearbyShops.adapter = nearShopAdapter

        nearShopAdapter.onClickListener = { pos, clickOn ->

            if (getPreference(AppConstants.PROFILE_TYPE, "") == "guest") {
                (context as MainActivity).showLoginDialog()
            } else {
                if (clickOn == "favourite") {
                    selectedPos = pos
                    favUnFavApiHit(list[pos])
                } else if (clickOn == "itemClick") {
                    val intent = Intent(requireContext(), ShopDetailActivity::class.java)
                    intent.putExtra(AppConstants.SHOP_ID, list[pos].id.toString())
                    startActivity(intent)
                } else if (clickOn == "rating") {
                    if (getPreference(AppConstants.PROFILE_TYPE, "") == AppConstants.GUEST) {
                        (requireContext().applicationContext as MainActivity).showLoginDialog()
                    } else {
                        val intent = Intent(requireContext(), ShopReviewsActivity::class.java)
                        intent.putExtra(AppConstants.SHOP_LISTING_RESPONSE, list[pos])
                        startActivity(intent)
                    }

                }
            }


        }
    }


    private fun shopListingApi() {
        val hashMap = HashMap<String, String>()
        hashMap["latitude"] = "30.7333" //for testing
        hashMap["longitude"] = "76.7794" //for testing
        /*hashMap["latitude"] = mLatitude
        hashMap["longitude"] = mLongitude*/


        appViewModel.shopListApi(requireActivity(), true, hashMap)
        appViewModel.getResponse().observe(requireActivity(), this)

    }

    private fun favUnFavApiHit(shopResponse: ShopListingResponse.Body.Shop) {
        val hashMap = HashMap<String, String>()
        hashMap["vendorId"] = shopResponse.userId.toString()

        if (shopResponse.isLiked == 1)
            hashMap["status"] = "0"
        else {
            hashMap["status"] = "1"
        }

        appViewModel.addFavApi(requireActivity(), true, hashMap)
        appViewModel.getResponse().observe(requireActivity(), this)
    }

    override fun onChanged(t: RestObservable?) {
        when (t!!.status) {
            Status.SUCCESS -> {
                if (t.data is ShopListingResponse) {
                    val response: ShopListingResponse = t.data

                    if (response.code == AppConstants.SUCCESS_CODE) {
                        list.clear()
                        list.addAll(t.data.body.shopList)

                        setShopAdapter()

                        if (response.body.notificationCount == 0) {
                            binding.notifyRedBG.visibility = View.GONE
                        } else {
                            binding.notifyRedBG.visibility = View.VISIBLE
                        }

                        if (response.body.cartCount == 0) {
                            binding.ivRedCart.visibility = View.GONE
                        } else {
                            binding.ivRedCart.visibility = View.VISIBLE
                        }

                    }

                } else if (t.data is AddFavouriteResponse) {
                    val response: AddFavouriteResponse = t.data

                    if (response.code == AppConstants.SUCCESS_CODE) {

                        list[selectedPos!!].isLiked = response.body.status
                        nearShopAdapter.notifyDataSetChanged()
                        AppUtils.showMsgOnlyWithoutClick(requireActivity(), response.message)

                    }

                }

            }
        }


    }


}