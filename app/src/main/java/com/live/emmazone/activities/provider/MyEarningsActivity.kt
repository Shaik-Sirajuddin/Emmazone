package com.live.emmazone.activities.provider

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.live.emmazone.R
import com.live.emmazone.adapter.AdapterMyEarnings
import com.live.emmazone.databinding.ActivityMyEarningsBinding
import com.live.emmazone.model.ModelMyEarnings
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.MyEarningResponse
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.view_models.AppViewModel

class MyEarningsActivity : AppCompatActivity(), Observer<RestObservable> {
    lateinit var binding: ActivityMyEarningsBinding
    lateinit var adapter: AdapterMyEarnings
    val list = ArrayList<ModelMyEarnings>()
    val orders = ArrayList<MyEarningResponse.Body.FindOrder>()
    private val appViewModel: AppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyEarningsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = AdapterMyEarnings(orders)
        binding.rvMyEarnings.adapter = adapter

        clicksHandle()
        earningApiHit("1")
    }

    private fun clicksHandle() {
        binding.back.setOnClickListener {
            onBackPressed()
        }

        binding.tvToday.setOnClickListener {
            todayClk()
        }

        binding.tvWeekly.setOnClickListener {
            weeklyClick()
        }
    }
    private fun openWithdrawalActivity(){
        val intent = Intent(this, Withdrawal2Activity::class.java)
        startActivity(intent)
    }
    private fun todayClk() {
        binding.tvToday.background =
            ContextCompat.getDrawable(this, R.drawable.bg_fill_earning)
        binding.tvWeekly.setBackgroundColor(Color.TRANSPARENT)

        earningApiHit("1") //1 for ToDay Earnings
    }


    private fun weeklyClick() {
        binding.tvWeekly.background =
            ContextCompat.getDrawable(this, R.drawable.bg_fill_earning)
        binding.tvToday.setBackgroundColor(Color.TRANSPARENT)

        earningApiHit("2") //2 for Weekly Earning
    }

    private fun earningApiHit(status: String) {
        val hashMap = HashMap<String, String>()
        hashMap["status"] = status

        appViewModel.myEarning(this, hashMap, true)
        appViewModel.getResponse().observe(this, this)
    }

    override fun onChanged(t: RestObservable?) {
        when (t!!.status) {
            Status.SUCCESS -> {
                if (t.data is MyEarningResponse) {
                    val response: MyEarningResponse = t.data

                    if (response.code == AppConstants.SUCCESS_CODE) {
                        binding.totalEarning.text =
                            getString(R.string.euro_symbol, response.body.totalEarning.toString())

                        binding.totalProducts.text = response.body.totalProduct.toString()
                        orders.clear()
                        orders.addAll(response.body.findOrder)
                        adapter.notifyDataSetChanged()
                    }

                }
            }
            else -> {}
        }
    }
}