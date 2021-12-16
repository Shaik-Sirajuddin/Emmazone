package com.live.emmazone.activities.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.live.emmazone.MainActivity
import com.live.emmazone.R
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
            val dialog = Dialog(requireContext())
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)
            dialog.setCanceledOnTouchOutside(true)
            dialog.window?.apply {

                setLayout(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)

                setBackgroundDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        android.R.color.transparent
                    )
                )
            }

            dialog.setContentView(R.layout.dialog_scan_qr_code)

            val backIcon = dialog.findViewById<ImageView>(R.id.crossImage)

            backIcon.setOnClickListener { dialog.dismiss() }

            dialog.show()
        }

        scannerPickCollect.setOnClickListener {

            val dialog = Dialog(requireContext())
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)
            dialog.setCanceledOnTouchOutside(true)
            dialog.window?.apply {

                setLayout(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)

                setBackgroundDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        android.R.color.transparent
                    )
                )
            }

            dialog.setContentView(R.layout.dialog_scan_qr_code)

            val backIcon = dialog.findViewById<ImageView>(R.id.crossImage)

            backIcon.setOnClickListener { dialog.dismiss() }

            dialog.show()
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