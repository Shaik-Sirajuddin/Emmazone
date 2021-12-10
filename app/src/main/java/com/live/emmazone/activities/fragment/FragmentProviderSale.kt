package com.live.emmazone.activities.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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

        openSalesFragment(NewSalesProviderFragment())

        tvNewsale.setOnClickListener {
            openSalesFragment(NewSalesProviderFragment())
        }

        tvongoingSale.setOnClickListener {
            openSalesFragment(OnGoingSalesProviderFragment())
        }

        tvPastSale.setOnClickListener {
            openSalesFragment(PastSalesProviderFragment())
        }

        imgNotify.setOnClickListener {
            val intent = Intent(activity, Notifications::class.java)
            startActivity(intent)
        }

        return view
    }

    private fun openSalesFragment(fragment : Fragment) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.fragmentProviderContainer, fragment)
        //  transaction?.addToBackStack("My Orders")
        transaction?.commit()
    }

}