package com.live.emmazone.activities.provider

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.live.emmazone.R
import com.live.emmazone.databinding.ActivityProviderShopDetailBinding

class ProviderShopDetailActivity : AppCompatActivity() {
    lateinit var binding : ActivityProviderShopDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProviderShopDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }
}