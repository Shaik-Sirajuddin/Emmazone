package com.live.emmazone.activities.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.live.emmazone.R
import com.live.emmazone.adapter.AdapterOrderDetail
import com.live.emmazone.databinding.ActivityReservedDeliveredDetailBinding
import com.live.emmazone.model.ModelOrderDetail

class ReservedDeliveredDetail : AppCompatActivity() {
    lateinit var binding : ActivityReservedDeliveredDetailBinding
    val list = ArrayList<ModelOrderDetail>()
    lateinit var adapter : AdapterOrderDetail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReservedDeliveredDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        binding.back.setOnClickListener {
            onBackPressed()
        }

      binding.recyclerOrderDetail.layoutManager =  LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        list.add(ModelOrderDetail(R.drawable.green, "Brend Shoes", "3", "90.00$"))
        list.add(ModelOrderDetail(R.drawable.green, "Winter Sweeters", "1", "30.00$"))

        binding.recyclerOrderDetail.adapter = AdapterOrderDetail(list)

    }
}