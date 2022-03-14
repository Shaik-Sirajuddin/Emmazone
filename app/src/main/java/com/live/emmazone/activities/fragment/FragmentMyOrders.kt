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
import com.live.emmazone.R
import com.live.emmazone.activities.main.Cart
import com.live.emmazone.activities.main.Notifications

class FragmentMyOrders : Fragment() {

    companion object{
        lateinit var notifyRedBG:ImageView
        lateinit var ivRedCartDot:ImageView
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = LayoutInflater.from(context).inflate( R.layout.fragment_my_orders, container, false)

        val tvOnGoing : TextView = view.findViewById(R.id.tvOnGoingMyOrders)
        val tvPastOrders : TextView = view.findViewById(R.id.tvPastMyOrders)
        val imgNotifications : ImageView = view.findViewById(R.id.image_notifications)
        val imgCart : ImageView = view.findViewById(R.id.cart)
         notifyRedBG  = view.findViewById(R.id.notifyRedBG)
        ivRedCartDot  = view.findViewById(R.id.ivRedCart)
        val layoutSwitch : LinearLayout = view.findViewById(R.id.layoutMyOrders)

        imgNotifications.setOnClickListener {
            val intent = Intent(activity, Notifications::class.java)
            startActivity(intent)
        }

        imgCart.setOnClickListener {
            val intent = Intent(activity, Cart::class.java)
            startActivity(intent)
        }

        openOnGoingOrdersFragment(OnGoingOrdersFragment())

        tvOnGoing.setOnClickListener {
            openOnGoingOrdersFragment(OnGoingOrdersFragment())
            tvOnGoing.setBackgroundResource(R.drawable.bg_fill_earning)
            tvOnGoing.setTextColor(Color.WHITE)
            tvPastOrders.setTextColor(Color.BLACK)
            tvPastOrders.setBackgroundColor(Color.TRANSPARENT)
        }

        tvPastOrders.setOnClickListener {
            openOnGoingOrdersFragment(PastFragment())
            tvOnGoing.setBackgroundColor(Color.TRANSPARENT)
            tvOnGoing.setTextColor(Color.BLACK)
            tvPastOrders.setTextColor(Color.WHITE)
            tvPastOrders.setBackgroundResource(R.drawable.bg_fill_earning)
        }
        return view
    }

    private fun openOnGoingOrdersFragment(fragment : Fragment) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.fragmentContainerMyOrders, fragment)
      //  transaction?.addToBackStack("My Orders")
        transaction?.commit()
    }

}