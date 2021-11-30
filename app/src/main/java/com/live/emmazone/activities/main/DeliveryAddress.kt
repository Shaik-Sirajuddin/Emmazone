package com.live.emmazone.activities.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.live.emmazone.R
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

        recyclerView = findViewById(R.id.recyclerDeliveryAddress)

        list.add(ModelDeliveryAddress("John Marker", "260-C North EI Camino Real"))
        list.add(ModelDeliveryAddress("Jackson", "1186 Roseville pkwy"))
        list.add(ModelDeliveryAddress("John Marker", "260-C North EI Camino Real"))

        recyclerView.adapter = AdapterDeliveryAddress(list)
    }
}