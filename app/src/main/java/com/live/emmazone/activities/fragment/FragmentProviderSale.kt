package com.live.emmazone.activities.fragment

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.live.emmazone.R
import com.live.emmazone.activities.main.Notifications
import com.live.emmazone.databinding.FragmentSaleProviderBinding
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.CommonResponse
import com.live.emmazone.response_model.NotificationListingResponse
import com.live.emmazone.view_models.AppViewModel

class FragmentProviderSale(val notificationResponse: NotificationListingResponse.Body?) :
    Fragment() , Observer<RestObservable> {

    companion object {
        lateinit var imageRedDot: ImageView
    }

    private lateinit var binding: FragmentSaleProviderBinding
    private val appViewModel: AppViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSaleProviderBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        clicksHandle()


        if (notificationResponse != null) {
            //  orderStatus  0-> Pending  1-> on the way 2-> Delivered 3-> cancelled
                notificationReadApiHit()
            if (notificationResponse.orderStatus == 0) {
                newSaleClick()
            } else if (notificationResponse.orderStatus == 1) {
                onGoingClick()
            } else if (notificationResponse.orderStatus == 2||notificationResponse.orderStatus == 3) {
                pastClick()
            }

        } else {
            newSaleClick()
        }

    }

    private fun notificationReadApiHit() {
        val hashMap = HashMap<String, String>()
        hashMap["id"] = notificationResponse!!.id.toString()

        appViewModel.readNotificationApi(requireActivity(), hashMap, true)
        appViewModel.getResponse().observe(requireActivity(), this)
    }


    private fun clicksHandle() {

        imageRedDot = binding.notifyRedBG

        binding.imageNotifications.setOnClickListener {
            val intent = Intent(activity, Notifications::class.java)
            startActivity(intent)
        }

        binding.newSale.setOnClickListener {
            newSaleClick()
        }

        binding.onGoingSale.setOnClickListener {
            onGoingClick()
        }

        binding.pastSale.setOnClickListener {
            pastClick()
        }

    }

    private fun pastClick() {
        openSalesFragment(PastSalesProviderFragment())
        binding.onGoingSale.setTextColor(Color.BLACK)
        binding.onGoingSale.setBackgroundColor(Color.TRANSPARENT)
        binding.newSale.setTextColor(Color.BLACK)
        binding.salesLayout.setBackgroundResource(R.drawable.bg_earning)
        binding.newSale.setBackgroundColor(Color.TRANSPARENT)
        binding.pastSale.setTextColor(Color.WHITE)
        binding.pastSale.setBackgroundResource(R.drawable.bg_fill_earning)
    }

    private fun onGoingClick() {
        openSalesFragment(OnGoingSalesProviderFragment())
        binding.onGoingSale.setTextColor(Color.WHITE)
        binding.onGoingSale.setBackgroundResource(R.drawable.bg_fill_earning)
        binding.newSale.setTextColor(Color.BLACK)
        binding.newSale.setBackgroundColor(Color.TRANSPARENT)
        binding.salesLayout.setBackgroundResource(R.drawable.bg_earning)
        binding.pastSale.setTextColor(Color.BLACK)
        binding.pastSale.setBackgroundColor(Color.TRANSPARENT)
    }

    private fun newSaleClick() {
        openSalesFragment(NewSalesProviderFragment())
        binding.newSale.setBackgroundResource(R.drawable.bg_fill_earning)
        binding.newSale.setTextColor(Color.WHITE)
        binding.onGoingSale.setTextColor(Color.BLACK)
        binding.onGoingSale.setBackgroundColor(Color.TRANSPARENT)
        binding.pastSale.setTextColor(Color.BLACK)
        binding.pastSale.setBackgroundColor(Color.TRANSPARENT)
        binding.salesLayout.setBackgroundResource(R.drawable.bg_earning)
    }


    private fun openSalesFragment(fragment: Fragment) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.fragmentProviderSalesContainer, fragment)
        transaction?.commit()
    }

    override fun onChanged(t: RestObservable?) {
        when (t!!.status) {
            Status.SUCCESS -> {
                if (t.data is CommonResponse) {

                }
            }
        }
    }
}