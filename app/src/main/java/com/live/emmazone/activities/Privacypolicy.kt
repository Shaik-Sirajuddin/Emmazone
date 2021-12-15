package com.live.emmazone.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.live.emmazone.R
import com.live.emmazone.databinding.ActivityPrivacypolicyBinding

class Privacypolicy : AppCompatActivity() {
    lateinit var binding : ActivityPrivacypolicyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrivacypolicyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        binding.back.setOnClickListener {
            onBackPressed()
        }

    }
}