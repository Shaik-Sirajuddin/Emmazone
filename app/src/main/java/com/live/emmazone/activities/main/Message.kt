package com.live.emmazone.activities.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.live.emmazone.R
import com.live.emmazone.databinding.ActivityMessageBinding

class Message : AppCompatActivity() {
    lateinit var binding : ActivityMessageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}