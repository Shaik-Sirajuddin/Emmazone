package com.live.emmazone.activities.fragment

import android.app.AlertDialog
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
import com.live.emmazone.activities.Interface.OnItemClick
import com.live.emmazone.activities.main.OrderDetail
import com.live.emmazone.activities.main.ReservedDeliveredDetail
import com.live.emmazone.adapter.AdapterOnGoPickCollect
import com.live.emmazone.adapter.AdapterOnGoingOrders
import com.live.emmazone.model.ModelOnGoingOrders

class OnGoingOrdersFragment : Fragment() {

    var list = ArrayList<ModelOnGoingOrders>()
    var listPickupCollectOrder = ArrayList<ModelOnGoingOrders>()

    lateinit var adapter: AdapterOnGoingOrders
    lateinit var adapterpickCollect: AdapterOnGoPickCollect

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = LayoutInflater.from(context).inflate(R.layout.on_going_orders_fragment, container, false)

        val recyclerView : RecyclerView = view.findViewById(R.id.rvMyOrderOnGoing)
        val recyclerViewPastOrders : RecyclerView = view.findViewById(R.id.rvMyOrderOnGoingPickCollect)
        val imageStatusOnTheWay : Button = view.findViewById(R.id.btnStatusOnTheWay)
        val imageStatusPickCollect : ImageView = view.findViewById(R.id.imgStatusPickCollect)
        val scannerOnWay : ImageView = view.findViewById(R.id.imgCodeScanner)
        val scannerPickCollect : ImageView = view.findViewById(R.id.imgCodeScanner1)

        scannerOnWay.setOnClickListener {
            val alertDialog: AlertDialog.Builder = AlertDialog.Builder(context)
            val factory = LayoutInflater.from(context)
            val view: View = factory.inflate(R.layout.dialog_scan_qr_code, null)
            val backIcon = view.findViewById<ImageView>(R.id.crossImage)

            alertDialog.setView(view)
            alertDialog.show()
            alertDialog.setCancelable(true)
            backIcon.setOnClickListener {
                alertDialog.setCancelable(true)
            }

        }

        scannerPickCollect.setOnClickListener {
            val alertDialog: AlertDialog.Builder = AlertDialog.Builder(context)
            val factory = LayoutInflater.from(context)
            val view: View = factory.inflate(R.layout.dialog_scan_qr_code, null)
            val backIcon = view.findViewById<ImageView>(R.id.crossImage)

            alertDialog.setView(view)
            alertDialog.show()
            alertDialog.setCancelable(true)
            backIcon.setOnClickListener {
                alertDialog.setCancelable(true)
            }
        }

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
        list.add(ModelOnGoingOrders(R.drawable.shoes_square, "Brend Shoe",
            "02", "90.00€"))
        list.add(ModelOnGoingOrders(R.drawable.winter, "Winter Sweeters",
            "02", "30.00€"))

        recyclerView.adapter = AdapterOnGoingOrders(list)

        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        listPickupCollectOrder.clear()
        listPickupCollectOrder.add(ModelOnGoingOrders(R.drawable.shoes_square, "Brend Shoe",
            "02", "30.00€"))

        recyclerViewPastOrders.adapter = AdapterOnGoPickCollect(listPickupCollectOrder)

        return view
    }

}