package com.live.emmazone.activities.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.live.emmazone.databinding.ActivityProductReviewsBinding


class ProductReviewsActivity : AppCompatActivity() {
    lateinit var binding : ActivityProductReviewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductReviewsBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}