package com.live.emmazone.activities

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat.setBackground
import androidx.fragment.app.Fragment
import com.live.emmazone.activities.fragment.OnGoingOrdersFragment
import com.live.emmazone.R
import com.live.emmazone.activities.fragment.PastFragment
import com.live.emmazone.activities.main.Cart
import com.live.emmazone.activities.main.Notifications

class FragmentMyOrders : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = LayoutInflater.from(context).inflate( R.layout.fragment_my_orders, container, false)

        val tvOnGoing : TextView = view.findViewById(R.id.tvOnGoingMyOrders)
        val tvPastOrders : TextView = view.findViewById(R.id.tvPastMyOrders)
        val imgNotifications : ImageView = view.findViewById(R.id.image_notifications)
        val imgCart : ImageView = view.findViewById(R.id.cart)
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
        }

        tvPastOrders.setOnClickListener {
            openOnGoingOrdersFragment(PastFragment())
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