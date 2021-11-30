package com.live.emmazone.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.live.emmazone.MainActivity
import com.live.emmazone.R
import com.live.emmazone.activities.auth.LoginActivity
import com.live.emmazone.activities.auth.SignUpActivity
import com.live.emmazone.databinding.ActivityVerificationCodeBinding

class VerificationCode : AppCompatActivity() {
    lateinit var binding : ActivityVerificationCodeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerificationCodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        binding.imageArrowback.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.btnSubmit.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}