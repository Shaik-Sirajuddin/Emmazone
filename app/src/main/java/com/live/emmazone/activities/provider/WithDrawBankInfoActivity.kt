package com.live.emmazone.activities.provider

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.live.emmazone.R
import com.live.emmazone.databinding.ActivityWithDrawBankInfoBinding

class WithDrawBankInfoActivity : AppCompatActivity() {
    lateinit var binding : ActivityWithDrawBankInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWithDrawBankInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener {
            onBackPressed()
        }

        binding.btnWithdraw.setOnClickListener {
            val intent = Intent(this, MyEarningsActivity::class.java)
            startActivity(intent)
        }

    }
}