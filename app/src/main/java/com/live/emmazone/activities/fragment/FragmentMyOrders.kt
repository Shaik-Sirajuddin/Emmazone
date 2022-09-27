package com.live.emmazone.activities.fragment

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.live.emmazone.R
import com.live.emmazone.activities.main.Cart
import com.live.emmazone.activities.main.Notifications
import com.live.emmazone.databinding.FragmentMyOrdersBinding
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.CommonResponse
import com.live.emmazone.response_model.NotificationListingResponse
import com.live.emmazone.view_models.AppViewModel
import java.util.*
import kotlin.collections.HashMap

class FragmentMyOrders(
    private val notificationResponse: NotificationListingResponse.Body?) :
    Fragment(), Observer<RestObservable> {

    companion object {
        lateinit var notifyRedBG: ImageView
        lateinit var ivRedCartDot: ImageView
    }

    private lateinit var binding: FragmentMyOrdersBinding
    private val appViewModel: AppViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyOrdersBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notifyRedBG = view.findViewById(R.id.notifyRedBG)
        ivRedCartDot = view.findViewById(R.id.ivRedCart)

        binding.imageNotifications.setOnClickListener {
            val intent = Intent(activity, Notifications::class.java)
            startActivity(intent)
        }

        binding.cart.setOnClickListener {
            val intent = Intent(activity, Cart::class.java)
            startActivity(intent)
        }

        if (notificationResponse != null) {
            //  orderStatus  0-> Pending  1-> on the way 2-> Delivered 3-> cancelled
            notificationReadApiHit()
            if (notificationResponse.orderStatus == 0 || notificationResponse.orderStatus == 1) {
                onGoingClick()
            } else if (notificationResponse.orderStatus == 2 || notificationResponse.orderStatus == 3) {
                pastClick()
            }
        } else {
            onGoingClick()
        }


        binding.tvOnGoingMyOrders.setOnClickListener {
            onGoingClick()
        }

        binding.tvPastMyOrders.setOnClickListener {
            pastClick()
        }


    }

    private fun notificationReadApiHit() {
        val hashMap = HashMap<String, String>()
        hashMap["id"] = notificationResponse!!.id.toString()

        appViewModel.readNotificationApi(requireActivity(), hashMap, true)
        appViewModel.getResponse().observe(requireActivity(), this)
    }

    private fun pastClick() {
        openOnGoingOrdersFragment(PastFragment())
        binding.tvOnGoingMyOrders.setBackgroundColor(Color.TRANSPARENT)
        binding.tvOnGoingMyOrders.setTextColor(Color.BLACK)
        binding.tvPastMyOrders.setTextColor(Color.WHITE)
        binding.tvPastMyOrders.setBackgroundResource(R.drawable.bg_fill_earning)
    }

    private fun onGoingClick() {
        openOnGoingOrdersFragment(OnGoingOrdersFragment())
        binding.tvOnGoingMyOrders.setBackgroundResource(R.drawable.bg_fill_earning)
        binding.tvOnGoingMyOrders.setTextColor(Color.WHITE)
        binding.tvPastMyOrders.setTextColor(Color.BLACK)
        binding.tvPastMyOrders.setBackgroundColor(Color.TRANSPARENT)
    }

    private fun openOnGoingOrdersFragment(fragment: Fragment) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.fragmentContainerMyOrders, fragment)
        transaction?.commit()
    }

    override fun onChanged(t: RestObservable?) {
        when (t!!.status) {
            Status.SUCCESS -> {
                if (t.data is CommonResponse) {

                }
            }
            else -> {}
        }
    }

}