package com.live.emmazone.activities.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.live.emmazone.R
import com.live.emmazone.activities.AddNewAddress
import com.live.emmazone.adapter.AdapterDeliveryAddress
import com.live.emmazone.databinding.ActivityDeliveryAddressBinding
import com.live.emmazone.model.ModelDeliveryAddress

class DeliveryAddress : AppCompatActivity() {
    lateinit var binding : ActivityDeliveryAddressBinding
//    lateinit var list: ArrayList<ModelDeliveryAddress>
//    lateinit var adapter : AdapterDeliveryAddress

    var list = ArrayList<ModelDeliveryAddress>()
    lateinit var adapter: AdapterDeliveryAddress
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeliveryAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        binding.btnaddNewAddress.setOnClickListener {
            val intent = Intent(this, AddNewAddress::class.java)
            startActivity(intent)
        }

        binding.btnNext.setOnClickListener {
            onBackPressed()
        }
        binding.back.setOnClickListener {
            onBackPressed()
        }

        recyclerView = findViewById(R.id.recyclerDeliveryAddress)

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        list.add(ModelDeliveryAddress("John Marker", "260-C North EI Camino Real"))
        list.add(ModelDeliveryAddress("Jackson", "1186 Roseville pkwy"))
        list.add(ModelDeliveryAddress("John Marker", "260-C North EI Camino Real"))

        recyclerView.adapter = AdapterDeliveryAddress(list)
    }
}