package com.live.emmazone.activities.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.live.emmazone.MainActivity
import com.live.emmazone.databinding.ActivityUserLoginChoiceBinding
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.extensionfuncton.savePreference

class UserLoginChoice : AppCompatActivity() {
    lateinit var binding : ActivityUserLoginChoiceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserLoginChoiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignInUser.setOnClickListener {
//            saveProfileType(AppConstants.USER)
            savePreference(AppConstants.PROFILE_TYPE, AppConstants.USER)

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        binding.btnContinueAsGuest.setOnClickListener {
//            saveProfileType(AppConstants.GUEST)
            savePreference(AppConstants.PROFILE_TYPE, AppConstants.GUEST)

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        binding.tvCreateAccount.setOnClickListener {
//            saveProfileType(AppConstants.USER)
            savePreference(AppConstants.PROFILE_TYPE, AppConstants.USER)

            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
        binding.btnSignInSeller.setOnClickListener {
//            saveProfileType(AppConstants.SELLER)
            savePreference(AppConstants.PROFILE_TYPE, AppConstants.SELLER)
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}