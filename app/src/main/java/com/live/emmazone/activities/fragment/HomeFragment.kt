package com.live.emmazone.activities.fragment

import android.app.Activity.RESULT_OK
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.live.emmazone.MainActivity
import com.live.emmazone.R
import com.live.emmazone.activities.auth.LoginActivity
import com.live.emmazone.activities.auth.UserLoginChoice
import com.live.emmazone.activities.main.*
import com.live.emmazone.adapter.AdapterNearbyShops
import com.live.emmazone.databinding.FragmentHomeBinding
import com.live.emmazone.extensionfuncton.getPreference
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.AddFavouriteResponse
import com.live.emmazone.response_model.ShopListingResponse
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.utils.AppUtils
import com.live.emmazone.utils.LocationUpdateUtilityFragment
import com.live.emmazone.view_models.AppViewModel
import java.io.IOException
import java.util.*

class HomeFragment : LocationUpdateUtilityFragment(), Observer<RestObservable> {

    private val appViewModel: AppViewModel by viewModels()

    private var mLatitude = ""
    private var mLongitude = ""
    private var mDistance = ""

    private lateinit var binding: FragmentHomeBinding

    private val list = ArrayList<ShopListingResponse.Body.Shop>()
    lateinit var nearShopAdapter: AdapterNearbyShops
    private var selectedPos: Int? = null


    private val filterLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                mDistance = result.data?.getStringExtra(AppConstants.DISTANCE)!!
                mLatitude = result.data?.getStringExtra(AppConstants.LATITUDE)!!
                mLongitude = result.data?.getStringExtra(AppConstants.LONGITUDE)!!
                binding.tvLocation.text = result.data?.getStringExtra(AppConstants.LOCATION)!!

                if (mLatitude.isNotEmpty() && mLongitude.isNotEmpty()) {
                    shopListingApi()
                } else {
                    getLiveLocation(requireActivity())
                }

            }
        }

    override fun updatedLatLng(lat: Double?, lng: Double?) {
        if (lat != null && lng != null) {
            mLatitude = lat.toString()
            mLongitude = lng.toString()

            shopListingApi()
            stopLocationUpdates()
            binding.tvLocation.text = completedAddress(lat, lng)
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
            intent.putExtra(AppConstants.DISTANCE, mDistance)
            intent.putExtra(AppConstants.LATITUDE, mLatitude)
            intent.putExtra(AppConstants.LONGITUDE, mLongitude)
            intent.putExtra(AppConstants.LOCATION, binding.tvLocation.text)
            filterLauncher.launch(intent)
        }

        binding.imageNotifications.setOnClickListener {
            if (getPreference(AppConstants.PROFILE_TYPE, "") == AppConstants.GUEST) {
                (activity as MainActivity).showLoginDialog()
                return@setOnClickListener
            }
            val intent = Intent(activity, Notifications::class.java)
            startActivity(intent)
        }

        binding.edtSearchWishList.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                searchShopsFilter(s.toString())
            }

        })

        /*binding.imageLocationHome.setOnClickListener {
            locationEnableDialog()
        }*/
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
        if (list.size > 0) {
            binding.tvNoShop.visibility = View.GONE
        } else {
            binding.tvNoShop.visibility = View.VISIBLE
        }

        nearShopAdapter = AdapterNearbyShops(list)
        binding.rvHomeNearbyShops.adapter = nearShopAdapter

        nearShopAdapter.onClickListener = { shopModel, clickOn ->

            if (getPreference(AppConstants.PROFILE_TYPE, "") == "guest") {
                (context as MainActivity).showLoginDialog()
            } else {
                if (clickOn == "favourite") {
                    selectedPos = list.indexOf(shopModel)
                    favUnFavApiHit(shopModel)
                } else if (clickOn == "itemClick") {
                    val intent = Intent(requireContext(), ShopDetailActivity::class.java)
                    intent.putExtra(AppConstants.SHOP_ID, shopModel.id.toString())
                    startActivity(intent)
                } else if (clickOn == "rating") {
                    if (getPreference(AppConstants.PROFILE_TYPE, "") == AppConstants.GUEST) {
                        (requireContext().applicationContext as MainActivity).showLoginDialog()
                    } else {
                        val intent = Intent(requireContext(), ShopReviewsActivity::class.java)
                        intent.putExtra(AppConstants.SHOP_LISTING_RESPONSE, shopModel)
                        startActivity(intent)
                    }

                }
            }


        }
    }

    private fun searchShopsFilter(text: String) {
        val filterList = ArrayList<ShopListingResponse.Body.Shop>()

        list.forEach {
            if (it.shopName.contains(text, true)) {
                filterList.add(it)
            }
        }
        nearShopAdapter.notifyData(filterList)
    }

    private fun shopListingApi() {
        val hashMap = HashMap<String, String>()
//        hashMap["latitude"] = "30.7333" //for testing
//        hashMap["longitude"] = "76.7794" //for testing
        hashMap["latitude"] = mLatitude
        hashMap["longitude"] = mLongitude
        hashMap["min_distance"] = "0"
        hashMap["max_distance"] = mDistance

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
            Status.ERROR -> {
                if (list.size > 0) {
                    binding.tvNoShop.visibility = View.GONE
                } else {
                    binding.tvNoShop.visibility = View.VISIBLE
                }
            }
        }


    }

    override fun onResume() {
        super.onResume()
//        getLiveLocation(requireActivity())
    }

    private fun completedAddress(latitude: Double, longitude: Double): String {
        var addresses: List<Address>? = null
        var city: String? = ""
        val geoCoder = Geocoder(requireContext(), Locale.getDefault())
        var location = ""

        try {
            addresses = geoCoder.getFromLocation(latitude, longitude, 1)
            if (addresses != null && addresses.isNotEmpty()) {
                val countryName = addresses[0].countryName
                val state = addresses[0].subAdminArea
                val address = addresses[0].featureName
                val island = addresses[0].adminArea
// val city = addresses[0].locality


                try {
                    city = addresses[0].locality
                    if (city == null) city = addresses[0].subLocality
                    if (city == null) city = addresses[0].subAdminArea
                    if (city == null) city = addresses[0].adminArea
// addressCity = city
                } catch (e: Exception) {
// addressCity = ""
                }

                val pinCode = addresses[0].postalCode
                val latitudeAddress = addresses[0].latitude
                val longitudeAddress = addresses[0].longitude

// If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                location = addresses[0].getAddressLine(0)


            }
        } catch (e: IOException) {
            e.printStackTrace()
            location = ""
        }

        return location
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }
}