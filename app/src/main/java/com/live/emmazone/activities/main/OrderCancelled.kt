package com.live.emmazone.activities.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.live.emmazone.R
import com.live.emmazone.databinding.ActivityOrderCancelledBinding

class OrderCancelled : AppCompatActivity() {
    lateinit var binding : ActivityOrderCancelledBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderCancelledBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}