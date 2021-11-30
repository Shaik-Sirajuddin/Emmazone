package com.live.emmazone.activities.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.live.emmazone.R
import com.live.emmazone.databinding.ActivityShopReviewsBinding

class ShopReviewsActivity : AppCompatActivity() {
    lateinit var binding : ActivityShopReviewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopReviewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}