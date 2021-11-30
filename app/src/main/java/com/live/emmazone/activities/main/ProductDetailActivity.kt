package com.live.emmazone.activities.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.live.emmazone.R
import com.live.emmazone.databinding.ActivityProductDetailBinding

class ProductDetailActivity : AppCompatActivity()
{
    lateinit var binding : ActivityProductDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}