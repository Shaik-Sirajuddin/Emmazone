package com.live.emmazone.activities.provider

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.live.emmazone.R
import com.live.emmazone.adapter.AdapterMyEarnings
import com.live.emmazone.databinding.ActivityMyEarningsBinding
import com.live.emmazone.model.ModelMyEarnings

class MyEarningsActivity : AppCompatActivity() {
    lateinit var binding : ActivityMyEarningsBinding
    lateinit var adapter : AdapterMyEarnings
    val list = ArrayList<ModelMyEarnings>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyEarningsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.back.setOnClickListener {
            onBackPressed()
        }

        binding.btnWithdraw.setOnClickListener {
            val intent = Intent(this, WithdrawalActivity::class.java)
            startActivity(intent)
        }

        binding.rvMyEarnings.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        list.add(ModelMyEarnings("Brend Shoes", "6:00 PM on 03 sep,2020",
            "50€"))
        list.add(ModelMyEarnings("Brend Shoes", "8:00 PM on 03 sep,2020", "70€"))
        list.add(ModelMyEarnings("Brend Shoes", "6:00 PM on 03 sep,2020", "50€"))
        list.add(ModelMyEarnings("Brend Shoes", "8:00 PM on 03 sep,2020", "70€"))
        list.add(ModelMyEarnings("Brend Shoes", "6:00 PM on 03 sep,2020", "50€"))
        list.add(ModelMyEarnings("Brend Shoes", "8:00 PM on 03 sep,2020", "70€"))

        binding.rvMyEarnings.adapter = AdapterMyEarnings(list)

    }
}