package com.live.emmazone.activities.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.live.emmazone.MainActivity
import com.live.emmazone.R
import com.live.emmazone.activities.auth.LoginActivity
import com.live.emmazone.activities.auth.UserLoginChoice
import com.live.emmazone.activities.main.*
import com.live.emmazone.utils.Constants
import com.live.emmazone.utils.helper.getProfileType

class FragmentHome : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_home, container, false)

        val imageFilter = view.findViewById<ImageView>(R.id.imageFilterHome)
        val imageLocation = view.findViewById<ImageView>(R.id.imageLocationHome)
        val imageNotification = view.findViewById<ImageView>(R.id.image_notifications)
        val itemImageHome = view.findViewById<ImageView>(R.id.itemImageHome)
        val ratingBar = view.findViewById<ImageView>(R.id.ratingBarWishList)
        val cart = view.findViewById<ImageView>(R.id.cart)
        val btnClickHereHome = view.findViewById<Button>(R.id.btnClickHereHome)
        val tvUserName = view.findViewById<TextView>(R.id.tvUserName)

        if (getProfileType() == "guest") {
            tvUserName.text = "Guest"
        }

        btnClickHereHome.setOnClickListener {
            if (getProfileType() == "guest") {
                startActivity(Intent(activity, UserLoginChoice::class.java))
            } else {
                startActivity(Intent(activity, LoginActivity::class.java))
            }
        }

        cart.setOnClickListener {
            if (getProfileType() == Constants.GUEST) {
                (activity as MainActivity).showLoginDialog()
                return@setOnClickListener
            }
            val intent = Intent(activity, Cart::class.java)
            startActivity(intent)
        }

        ratingBar.setOnClickListener {
            if (getProfileType() == Constants.GUEST) {
                (activity as MainActivity).showLoginDialog()
                return@setOnClickListener
            }
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
            if (getProfileType() == Constants.GUEST) {
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

}