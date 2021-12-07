package com.live.emmazone.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.live.emmazone.OnGoingOrdersFragment
import com.live.emmazone.R

class FragmentMyOrders : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = LayoutInflater.from(context).inflate( R.layout.fragment_my_orders, container, false)

        val tvOnGoing : TextView = view.findViewById(R.id.tvOnGoingMyOrders)
        val tvPastOrders : TextView = view.findViewById(R.id.tvPastMyOrders)

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
        transaction?.addToBackStack("My Orders")
        transaction?.commit()
    }

}