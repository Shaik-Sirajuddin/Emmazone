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
import com.live.emmazone.activities.main.Notifications

class FragmentProviderSale : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       val view : View = LayoutInflater.from(context).inflate(R.layout.fragment_sale_provider, container, false)

        val imgNotify : ImageView = view.findViewById(R.id.image_notifications)
        val tvNewsale : TextView = view.findViewById(R.id.NewSale)
        val tvongoingSale : TextView = view.findViewById(R.id.OnGoingSale)
        val tvPastSale : TextView = view.findViewById(R.id.pastSale)
        val salesLayout : LinearLayout = view.findViewById(R.id.salesLayout)

        openSalesFragment(NewSalesProviderFragment())

        tvNewsale.setOnClickListener {
            openSalesFragment(NewSalesProviderFragment())
            tvNewsale.setBackgroundResource(R.drawable.bg_fill_earning)
            tvNewsale.setTextColor(Color.WHITE)
            tvongoingSale.setTextColor(Color.BLACK)
            tvongoingSale.setBackgroundColor(Color.TRANSPARENT)
            tvPastSale.setTextColor(Color.BLACK)
            tvPastSale.setBackgroundColor(Color.TRANSPARENT)
            salesLayout.setBackgroundResource(R.drawable.bg_earning)
        }

        tvongoingSale.setOnClickListener {
            openSalesFragment(OnGoingSalesProviderFragment())
            tvongoingSale.setTextColor(Color.WHITE)
            tvongoingSale.setBackgroundResource(R.drawable.bg_fill_earning)
            tvNewsale.setTextColor(Color.BLACK)
            tvNewsale.setBackgroundColor(Color.TRANSPARENT)
            salesLayout.setBackgroundResource(R.drawable.bg_earning)
            tvPastSale.setTextColor(Color.BLACK)
            tvPastSale.setBackgroundColor(Color.TRANSPARENT)
        }

        tvPastSale.setOnClickListener {
            openSalesFragment(PastSalesProviderFragment())
            tvongoingSale.setTextColor(Color.BLACK)
            tvongoingSale.setBackgroundColor(Color.TRANSPARENT)
            tvNewsale.setTextColor(Color.BLACK)
            salesLayout.setBackgroundResource(R.drawable.bg_earning)
            tvNewsale.setBackgroundColor(Color.TRANSPARENT)
            tvPastSale.setTextColor(Color.WHITE)
            tvPastSale.setBackgroundResource(R.drawable.bg_fill_earning)
        }

        imgNotify.setOnClickListener {
            val intent = Intent(activity, Notifications::class.java)
            startActivity(intent)
        }

        return view
    }

    private fun openSalesFragment(fragment : Fragment) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.fragmentProviderSalesContainer, fragment)
        //  transaction?.addToBackStack("My Orders")
        transaction?.commit()
    }

}