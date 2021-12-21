package com.live.emmazone.activities.fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.live.emmazone.MainActivity
import com.live.emmazone.R
import com.live.emmazone.activities.auth.LoginActivity
import com.live.emmazone.activities.main.*
import com.live.emmazone.utils.helper.getProfileType

class FragmentHome : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = LayoutInflater.from(context).inflate( R.layout.fragment_home, container, false)

        val imageFilter = view.findViewById<ImageView>(R.id.imageFilterHome)
        val imageLocation = view.findViewById<ImageView>(R.id.imageLocationHome)
        val imageNotification = view.findViewById<ImageView>(R.id.image_notifications)
        val itemImageHome = view.findViewById<ImageView>(R.id.itemImageHome)
        val ratingBar = view.findViewById<ImageView>(R.id.ratingBarWishList)
        val cart = view.findViewById<ImageView>(R.id.cart)
        val btnClickHereHome = view.findViewById<Button>(R.id.btnClickHereHome)
        val tvUserName = view.findViewById<TextView>(R.id.tvUserName)

        if (getProfileType() == "guest"){
            tvUserName.setText("Guest")
        }

        btnClickHereHome.setOnClickListener {
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }

        cart.setOnClickListener {
            val intent = Intent(activity, Cart::class.java)
            startActivity(intent)
        }

        ratingBar.setOnClickListener {
            val intent = Intent(activity, ShopReviewsActivity::class.java)
            startActivity(intent)
        }

        itemImageHome.setOnClickListener {
            val intent = Intent(activity, ShopDetailActivity::class.java)
            startActivity(intent)
        }

        imageFilter.setOnClickListener {
            val intent = Intent(activity, FilterActivity::class.java)
            startActivity(intent)
        }

        imageNotification.setOnClickListener {
            val intent = Intent(activity, Notifications::class.java)
            startActivity(intent)
        }

        imageLocation.setOnClickListener {
            val alertDialog = AlertDialog.Builder(context)
            val factory = LayoutInflater.from(context)
            val view : View = factory.inflate(R.layout.activity_enable_location_services, null)
            val crossIcon = view.findViewById<ImageView>(R.id.crossImage)

            alertDialog.setView(view)
            alertDialog.show()
            alertDialog.setCancelable(true)

            crossIcon.setOnClickListener {
              //  alertDialog.setCancelable(true)
                alertDialog.setCancelable(true)
                val intent = Intent(activity, MainActivity::class.java)
                startActivity(intent)
            }

        }
        return view
    }

}