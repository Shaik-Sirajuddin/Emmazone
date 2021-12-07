package com.live.emmazone.activities.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.live.emmazone.R
import com.live.emmazone.databinding.ActivityOrderDeliveredDetailBinding

class OrderDeliveredDetail : AppCompatActivity() {
    lateinit var binding : ActivityOrderDeliveredDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDeliveredDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

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