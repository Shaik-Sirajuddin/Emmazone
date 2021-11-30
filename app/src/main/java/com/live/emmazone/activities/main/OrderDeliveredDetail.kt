package com.live.emmazone.activities.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.live.emmazone.R
import com.live.emmazone.databinding.ActivityOrderDeliveredDetailBinding

class OrderDeliveredDetail : AppCompatActivity() {
    lateinit var binding : ActivityOrderDeliveredDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDeliveredDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}