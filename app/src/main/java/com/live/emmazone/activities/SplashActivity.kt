package com.live.emmazone.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.live.emmazone.R
import com.live.emmazone.activities.auth.UserLoginChoice

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        Handler().postDelayed({
            val intent = Intent(this, UserLoginChoice::class.java)
            startActivity(intent)
        }, 2000)

    }
}