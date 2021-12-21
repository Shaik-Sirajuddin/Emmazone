package com.live.emmazone.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.WindowManager
import com.live.emmazone.MainActivity
import com.live.emmazone.R
import com.live.emmazone.activities.auth.LoginActivity
import com.live.emmazone.activities.auth.SignUpActivity
import com.live.emmazone.activities.provider.AddShopDetailActivity
import com.live.emmazone.databinding.ActivityVerificationCodeBinding
import com.live.emmazone.utils.Constants
import com.live.emmazone.utils.helper.getProfileType

class VerificationCode : AppCompatActivity() {
    lateinit var binding: ActivityVerificationCodeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerificationCodeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.otpField1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0 != null) {
                    if (p0.length > 0) {
                        binding.otpField1.clearFocus()
                        binding.otpField2.requestFocus()
                        binding.otpField2.setCursorVisible(true)
                    }
                }
            }
        })

        binding.otpField2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0 != null) {
                    if (p0.length > 0) {
                        binding.otpField2.clearFocus()
                        binding.otpField3.requestFocus()
                        binding.otpField3.setCursorVisible(true)
                    }
                }
            }

        })

        binding.otpField3.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0 != null) {
                    if (p0.length > 0) {
                        binding.otpField3.clearFocus()
                        binding.otpField4.requestFocus()
                        binding.otpField4.setCursorVisible(true)
                    }
                }
            }
        })

        binding.imageArrowback.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.btnSubmit.setOnClickListener {
            finish()

            if (getProfileType() == Constants.SELLER) {
                startActivity(Intent(this, AddShopDetailActivity::class.java))
            } else {
                startActivity(Intent(this, MainActivity::class.java))
            }
        }

    }
}