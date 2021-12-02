package com.live.emmazone.activities

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.live.emmazone.R
import com.live.emmazone.activities.main.Cart
import com.live.emmazone.activities.main.Notifications

class FragmentMyOrders : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = LayoutInflater.from(context).inflate( R.layout.fragment_my_orders, container, false)

        val notifications = view.findViewById<ImageView>(R.id.image_notifications)
        val cart = view.findViewById<ImageView>(R.id.cart)

        cart.setOnClickListener {
            val intent = Intent(activity, Cart::class.java)
            startActivity(intent)
        }
        notifications.setOnClickListener {
            val intent = Intent(activity, Notifications::class.java)
            startActivity(intent)
        }

        return view
    }

}