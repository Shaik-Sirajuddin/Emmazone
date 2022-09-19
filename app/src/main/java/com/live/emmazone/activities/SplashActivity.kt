package com.live.emmazone.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.live.emmazone.MainActivity
import com.live.emmazone.R
import com.live.emmazone.activities.auth.UserLoginChoice
import com.live.emmazone.activities.provider.ProviderMainActivity
import com.live.emmazone.extensionfuncton.getPreference
import com.live.emmazone.utils.AppConstants

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Log.e("authKey", "Bearer " + getPreference(AppConstants.AUTHORIZATION, ""))

        Handler(Looper.getMainLooper()).postDelayed({
            if (getPreference(AppConstants.IS_LOGIN, false)) {

                Log.d(AppConstants.USER_ID, getPreference(AppConstants.USER_ID, ""))
                if (getPreference(AppConstants.ROLE, "") == AppConstants.SELLER_ROLE) {
                    startActivity(Intent(this, ProviderMainActivity::class.java))
                    finish()
                } else {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }

            } else {
                startActivity(Intent(this, UserLoginChoice::class.java))
                finish()
            }
        }, 1000)

        Log.e("AuthKey", getPreference(AppConstants.AUTHORIZATION, ""))

    }
}