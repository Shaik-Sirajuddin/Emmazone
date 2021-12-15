package com.live.emmazone.activities.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.live.emmazone.R
import com.live.emmazone.activities.TermsCondition
import com.live.emmazone.activities.VerificationCode
import com.live.emmazone.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    lateinit var binding : ActivitySignUpBinding
    var flag = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.checkBox.setOnClickListener {

            if (flag == 0) {
                binding.checkBox.setImageResource(R.drawable.otp_bg)
                flag = 1

            } else if (flag == 1) {
                binding.checkBox.setImageResource(R.drawable.checkbox_tick)
                flag = 2

            } else if (flag == 2) {
                binding.checkBox.setImageResource(R.drawable.otp_bg)

                flag = 1
            }

        }

        binding.imageArrowback.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.termsConditions.setOnClickListener {
            val intent = Intent(this, TermsCondition::class.java)
            startActivity(intent)
        }
        binding.btnSignUp.setOnClickListener {
            val intent = Intent(this, VerificationCode::class.java)
            startActivity(intent)
        }
        binding.tvSignIn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}