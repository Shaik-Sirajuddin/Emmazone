package com.live.emmazone.activities.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.live.emmazone.R
import com.live.emmazone.adapter.AdapterOrderDetail
import com.live.emmazone.databinding.ActivityOrderDeliveredDetailBinding
import com.live.emmazone.model.ModelOrderDetail

class OrderDeliveredDetail : AppCompatActivity() {
    lateinit var binding : ActivityOrderDeliveredDetailBinding
    lateinit var adapter : AdapterOrderDetail
    val list = ArrayList<ModelOrderDetail>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDeliveredDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.recyclerOrderDeliveredDetail.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        list.add(ModelOrderDetail(R.drawable.shoes_square, "Brend Shoes",
            "3", "90.00€"))
        list.add(ModelOrderDetail(R.drawable.winter, "Winter Sweeters",
            "1", "30.00€"))

    //    binding.recyclerOrderDeliveredDetail.adapter = AdapterOrderDetail(list)


        binding.back.setOnClickListener {
            onBackPressed()
        }

        binding.btnBuyAgain.setOnClickListener {
            val intent = Intent(this, Cart::class.java)
            startActivity(intent)
        }

        binding.btnYourReviews.setOnClickListener {
            val intent = Intent(this, ProductReviewsActivity::class.java)
            startActivity(intent)
        }

    }
}