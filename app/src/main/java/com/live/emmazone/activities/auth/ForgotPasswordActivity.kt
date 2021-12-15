package com.live.emmazone.activities.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.live.emmazone.R
import com.live.emmazone.activities.VerificationCode
import com.live.emmazone.databinding.ActivityForgotPasswordBinding

class ForgotPasswordActivity : AppCompatActivity() {
    lateinit var binding : ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

      binding.imageArrowback.setOnClickListener {
          onBackPressed()
      }

        binding.btnSubmit.setOnClickListener {
            val intent = Intent(this, VerificationCode::class.java)
            startActivity(intent)
        }

    }
}