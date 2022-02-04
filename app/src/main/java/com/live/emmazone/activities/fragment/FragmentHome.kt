package com.live.emmazone.activities.fragment

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.live.emmazone.MainActivity
import com.live.emmazone.R
import com.live.emmazone.activities.auth.LoginActivity
import com.live.emmazone.activities.auth.UserLoginChoice
import com.live.emmazone.activities.main.*
import com.live.emmazone.adapter.AdapterNearbyShops
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.extensionfuncton.getPreference
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.ChangePasswordResponse
import com.live.emmazone.response_model.ShopListingResponse
import com.live.emmazone.utils.AppUtils
import com.live.emmazone.utils.LocationUpdateUtilityFragment
import com.live.emmazone.view_models.AppViewModel

class FragmentHome : LocationUpdateUtilityFragment(),Observer<RestObservable> {

    private val appViewModel: AppViewModel by viewModels()

    var globalLatitude = ""
    var globalLongitude = ""

    private val list = ArrayList<ShopListingResponse.Body>()
    lateinit var adapter : AdapterNearbyShops
    lateinit var recyclerView: RecyclerView

    override fun updatedLatLng(lat: Double?, lng: Double?) {
        if (lat != null && lng!=null){
            globalLatitude = lat.toString()
            globalLongitude = lng.toString()

            shopListingApi()
            stopLocationUpdates()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_home, container, false)

        val imageFilter = view.findViewById<ImageView>(R.id.imageFilterHome)
        val imageLocation = view.findViewById<ImageView>(R.id.imageLocationHome)
        val imageNotification = view.findViewById<ImageView>(R.id.image_notifications)
        val cart = view.findViewById<ImageView>(R.id.cart)
        val btnClickHereHome = view.findViewById<Button>(R.id.btnClickHereHome)
        val tvUserName = view.findViewById<TextView>(R.id.tvUserName)
        recyclerView = view.findViewById(R.id.rvHomeNearbyShops)

        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

//        list.add(
//            ModelNearbyShop(R.drawable.img4, "Berserka Store",
//            R.drawable.rating, "4.5/5",  "03 miles away", R.drawable.heart))
//
//        list.add(
//            ModelNearbyShop(R.drawable.img2, "Curve Store",
//                R.drawable.rating, "4.5/5",  "03 miles away", R.drawable.heart))
//
//        list.add(
//            ModelNearbyShop(R.drawable.img2, "Berserka Store",
//                R.drawable.rating, "4.5/5",  "03 miles away", R.drawable.heart))

//        recyclerView.adapter = context?.let { AdapterNearbyShops(it,list) }

        if ( getPreference(AppConstants.PROFILE_TYPE,"") == "guest") {
            tvUserName.text = "Guest"
        }

        btnClickHereHome.setOnClickListener {
            if ( getPreference(AppConstants.PROFILE_TYPE,"") == "guest") {
                startActivity(Intent(activity, UserLoginChoice::class.java))
            } else {
                startActivity(Intent(activity, LoginActivity::class.java))
            }
        }

        cart.setOnClickListener {
            if ( getPreference(AppConstants.PROFILE_TYPE,"") == AppConstants.GUEST) {
                (activity as MainActivity).showLoginDialog()
                return@setOnClickListener
            }
            val intent = Intent(activity, Cart::class.java)
            startActivity(intent)
        }

//        ratingBar.setOnClickListener {
//            if ( getPreference(AppConstants.PROFILE_TYPE,"") == AppConstants.GUEST) {
//                (activity as MainActivity).showLoginDialog()
//                return@setOnClickListener
//            }
//            val intent = Intent(activity, ShopReviewsActivity::class.java)
//            startActivity(intent)
//        }

//        itemImageHome.setOnClickListener {
//            val intent = Intent(activity, ShopDetailActivity::class.java)
//            startActivity(intent)
//        }

        imageFilter.setOnClickListener {
            val intent = Intent(activity, FilterActivity::class.java)
            startActivity(intent)
        }

        imageNotification.setOnClickListener {
            if ( getPreference(AppConstants.PROFILE_TYPE,"") == AppConstants.GUEST) {
                (activity as MainActivity).showLoginDialog()
                return@setOnClickListener
            }
            val intent = Intent(activity, Notifications::class.java)
            startActivity(intent)
        }

        imageLocation.setOnClickListener {

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
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        getLiveLocation(requireActivity())


    }

    private fun shopListingApi(){
        val hashMap = HashMap<String, String>()
        hashMap["latitude"] = globalLatitude
        hashMap["longitude"] = globalLongitude
        hashMap["distance"] = "4"

        appViewModel.shopListApi(requireActivity(), true,hashMap)
        appViewModel.getResponse().observe(this, this)

    }

    override fun onChanged(t: RestObservable?) {
        when (t!!.status) {
            Status.SUCCESS -> {
                if (t.data is ShopListingResponse) {
                    val response: ShopListingResponse = t.data

                    list.removeAll(t.data.body)
                    list.addAll(t.data.body)

                      adapter= context?.let { AdapterNearbyShops(it, list) }!!
                    recyclerView.adapter = adapter

                    adapter?.onClickListener = { pos, type ->
                        if (type.equals("1")){

                        }else if (type.equals("2")){
                            val intent = Intent(requireContext(), ShopDetailActivity::class.java)
                            startActivity(intent)
                        } else if (type.equals("3")){

                            if ( getPreference(AppConstants.PROFILE_TYPE,"") == AppConstants.GUEST) {
                                (requireContext().applicationContext as MainActivity).showLoginDialog()
//                                return@setOnClickListener
                            }
                            val intent = Intent(requireContext(), ShopReviewsActivity::class.java)
                            startActivity(intent)
                        }



                    }

                }
            }


        }
    }

}