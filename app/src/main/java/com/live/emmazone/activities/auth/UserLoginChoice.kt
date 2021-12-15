package com.live.emmazone.activities.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.live.emmazone.MainActivity
import com.live.emmazone.R
import com.live.emmazone.activities.provider.ProviderMainActivity
import com.live.emmazone.databinding.ActivityUserLoginChoiceBinding

class UserLoginChoice : AppCompatActivity() {
    lateinit var binding : ActivityUserLoginChoiceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserLoginChoiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignInUser.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        binding.btnContinueAsGuest.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        binding.tvCreateAccount.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
        binding.btnSignInSeller.setOnClickListener {
            val intent = Intent(this, ProviderMainActivity::class.java)
            startActivity(intent)
        }
    }
}