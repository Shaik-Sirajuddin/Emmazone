package com.live.emmazone.activities.provider

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.live.emmazone.R
import com.live.emmazone.adapter.AdapterProvODNewSalesStatus
import com.live.emmazone.databinding.ActivityOrderDetailPendingBinding
import com.live.emmazone.model.ModelNewSaleOrderDetail
import com.live.emmazone.model.ModelOnGoingOrders

class OrderDetailPendingActivity : AppCompatActivity() {
    lateinit var binding : ActivityOrderDetailPendingBinding
    val list = ArrayList<ModelNewSaleOrderDetail>()
    val listChildRecycler = ArrayList<ModelOnGoingOrders>()
    lateinit var adapter : AdapterProvODNewSalesStatus

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDetailPendingBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.back.setOnClickListener {
            onBackPressed()
        }

        binding.btnReadyDelivery.setOnClickListener {
            onBackPressed()
        }

        binding.rvOrderDetailPending.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        listChildRecycler.add(ModelOnGoingOrders(R.drawable.shoes_square, "Brend Shoe",
            "03", "90.00€", status = ""))
        listChildRecycler.add(ModelOnGoingOrders(R.drawable.shoes_square,
            "Brend Shoe", "03", "90.00€",status = ""))

        list.add(ModelNewSaleOrderDetail("PLU9540572", R.drawable.avtarr_girl,
            "Allen Chandler",
            R.drawable.chat, listChildRecycler))

        binding.rvOrderDetailPending.adapter = AdapterProvODNewSalesStatus(this, list)

    }

}