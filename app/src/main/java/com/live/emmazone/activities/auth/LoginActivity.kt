package com.live.emmazone.activities.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.WindowManager
import com.live.emmazone.MainActivity
import com.live.emmazone.R
import com.live.emmazone.activities.provider.AddShopDetailActivity
import com.live.emmazone.activities.provider.ProviderMainActivity
import com.live.emmazone.databinding.ActivityLoginBinding
import com.live.emmazone.utils.Constants
import com.live.emmazone.utils.helper.getProfileType

class LoginActivity : AppCompatActivity() {

    lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvForgotPwd.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }

        binding.btnSignIn.setOnClickListener {

            if (getProfileType() == Constants.SELLER) {
                startActivity(Intent(this, ProviderMainActivity::class.java))
            } else {
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
        binding.tvCreateAccount.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

    }
}