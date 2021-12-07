package com.live.emmazone.activities.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.live.emmazone.R
import com.live.emmazone.adapter.AdapterOrderDetail
import com.live.emmazone.databinding.ActivityOrderDetailBinding
import com.live.emmazone.model.ModelOrderDetail

class OrderDetail : AppCompatActivity() {
    lateinit var binding : ActivityOrderDetailBinding
    lateinit var adapter : AdapterOrderDetail
    val list = ArrayList<ModelOrderDetail>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        binding.back.setOnClickListener {
            onBackPressed()
        }

        binding.recyclerOrderDetail.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        list.add(ModelOrderDetail(R.drawable.green, "Brend Shoes", "3", "90.00$"))
        list.add(ModelOrderDetail(R.drawable.green, "Winter Sweeters", "1", "30.00$"))

        binding.recyclerOrderDetail.adapter = AdapterOrderDetail(list)

    }
}