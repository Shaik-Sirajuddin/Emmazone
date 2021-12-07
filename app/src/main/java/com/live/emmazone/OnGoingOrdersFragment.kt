package com.live.emmazone

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.live.emmazone.activities.main.OrderDetail
import com.live.emmazone.activities.main.ReservedDeliveredDetail
import com.live.emmazone.adapter.AdapterOnGoingOrders
import com.live.emmazone.adapter.AdapterWishList
import com.live.emmazone.model.ModelOnGoingOrders
import com.live.emmazone.model.ModelWishList

class OnGoingOrdersFragment : Fragment() {

    var list = ArrayList<ModelOnGoingOrders>()
    var listPickupCollectOrder = ArrayList<ModelOnGoingOrders>()

    lateinit var adapter: AdapterOnGoingOrders

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = LayoutInflater.from(context).inflate(R.layout.on_going_orders_fragment, container, false)

        val recyclerView : RecyclerView = view.findViewById(R.id.rvMyOrderOnGoing)
        val recyclerViewPastOrders : RecyclerView = view.findViewById(R.id.rvMyOrderOnGoingPickCollect)
        val imageStatusOnTheWay : ImageView = view.findViewById(R.id.imgStatusOnTheWay)
        val imageStatusPickCollect : ImageView = view.findViewById(R.id.imgStatusPickCollect)

        imageStatusPickCollect.setOnClickListener {
            val intent = Intent(activity, ReservedDeliveredDetail::class.java)
            startActivity(intent)
        }

        imageStatusOnTheWay.setOnClickListener {
            val intent = Intent(activity, OrderDetail::class.java)
            startActivity(intent)
        }

        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerViewPastOrders.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        list.clear()
        list.add(ModelOnGoingOrders(R.drawable.shoes_square, "Brend Shoe", "02", "90.00$"))
        list.add(ModelOnGoingOrders(R.drawable.shoes_square, "Brend Shoe", "02", "30.00$"))

        recyclerView.adapter = AdapterOnGoingOrders(list)

        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        listPickupCollectOrder.clear()
        listPickupCollectOrder.add(ModelOnGoingOrders(R.drawable.shoes_square, "Brend Shoe", "02", "30.00$"))

        recyclerViewPastOrders.adapter = AdapterOnGoingOrders(listPickupCollectOrder)

        return view
    }

}