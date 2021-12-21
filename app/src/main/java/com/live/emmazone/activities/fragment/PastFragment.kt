package com.live.emmazone.activities.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.live.emmazone.R
import com.live.emmazone.activities.listeners.OnActionListenerNew
import com.live.emmazone.activities.main.Cart
import com.live.emmazone.activities.main.OrderCancelled
import com.live.emmazone.activities.main.OrderDeliveredDetail
import com.live.emmazone.adapter.AdapterOnGoingOrders
import com.live.emmazone.adapter.AdapterOrderCancel
import com.live.emmazone.model.ModelOnGoingOrders

class PastFragment : Fragment() {
    var list = ArrayList<ModelOnGoingOrders>()
    lateinit var adapter: AdapterOnGoingOrders
    lateinit var adapterOrderCancel: AdapterOrderCancel
    var listPastOrders = ArrayList<ModelOnGoingOrders>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = LayoutInflater.from(context).inflate(R.layout.past_fragment, container, false)

        val rvPastDelivered: RecyclerView = view.findViewById(R.id.rvMyPastDelivered)
        val rvCancelledOrder: RecyclerView = view.findViewById(R.id.rvMyOrderPastCancelled)
        val imageStatusDelivered: ImageView = view.findViewById(R.id.imgStatusDelivered)
        val imageStatusCancel: ImageView = view.findViewById(R.id.imgStatusCancel)
        val buttonBuyAgain: Button = view.findViewById(R.id.btnBuyAgain)

        buttonBuyAgain.setOnClickListener {
            val intent = Intent(activity, Cart::class.java)
            startActivity(intent)
        }

        imageStatusDelivered.setOnClickListener {
            val intent = Intent(activity, OrderDeliveredDetail::class.java)
            startActivity(intent)
        }

        imageStatusCancel.setOnClickListener {
            val intent = Intent(activity, OrderCancelled::class.java)
            startActivity(intent)
        }


        rvPastDelivered.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvCancelledOrder.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        list.clear()
        list.add(
            ModelOnGoingOrders(
                R.drawable.shoes_square, "Brend Shoe",
                "02", "90.00€", status = "delivered"
            )
        )
        list.add(
            ModelOnGoingOrders(
                R.drawable.winter, "Winter Sweeters",
                "02", "30.00€", status = "delivered"
            )
        )

        val onActionListenerNew = object : OnActionListenerNew {
            override fun notifyOnClick() {
                imageStatusDelivered.performClick()
            }
        }
        rvPastDelivered.adapter =
            context?.let { AdapterOnGoingOrders(it, list, onActionListenerNew) }

        rvCancelledOrder.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        listPastOrders.clear()
        listPastOrders.add(
            ModelOnGoingOrders(
                R.drawable.shoes_square,
                "Brend Shoe", "02", "30.00€", status = "cancel"
            )
        )

        val onActionListenerCancel = object : OnActionListenerNew {
            override fun notifyOnClick() {
                imageStatusCancel.performClick()
            }
        }
        rvCancelledOrder.adapter = AdapterOrderCancel(listPastOrders, onActionListenerCancel)


        return view
    }
}