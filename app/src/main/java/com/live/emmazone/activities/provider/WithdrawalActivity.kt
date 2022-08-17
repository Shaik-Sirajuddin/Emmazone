package com.live.emmazone.activities.provider

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.live.emmazone.R
import com.live.emmazone.databinding.ActivityMainBinding
import com.live.emmazone.databinding.ActivityWithdrawalBinding

class WithdrawalActivity : AppCompatActivity() {
    lateinit var binding: ActivityWithdrawalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWithdrawalBinding.inflate(layoutInflater)
        setContentView(binding.root)

      binding.back.setOnClickListener {
          onBackPressed()
      }

        binding.btnAddBank.setOnClickListener {
           val intent = Intent(this, AddBankAccountActivity::class.java)
            startActivity(intent)
        }

    }
}