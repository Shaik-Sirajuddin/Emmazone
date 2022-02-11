package com.live.emmazone.activities.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.live.emmazone.R
import com.live.emmazone.adapter.AdapterAddPaymentCard
import com.live.emmazone.databinding.ActivityPaymentMethodBinding
import com.live.emmazone.model.ModelPaymentCard

class PaymentMethod : AppCompatActivity() {

    private lateinit var binding: ActivityPaymentMethodBinding
    private var isNotification = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentMethodBinding.inflate(layoutInflater)
        setContentView(binding.root)


        clicksHandle()
        setPaymentAdapter()


    }

    private fun clicksHandle() {
        binding.imgWallet.setOnClickListener {

            isNotification = !isNotification
            binding.imgWallet.setImageResource(
                if (isNotification)
                    R.drawable.radio_dot_circle
                else
                    R.drawable.radio_circle
            )

        }


        binding.btnNext.setOnClickListener {
            onBackPressed()
        }
        binding.back.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setPaymentAdapter() {
        val paymentAdapter = AdapterAddPaymentCard()
        binding.recyclerChooseCard.adapter = paymentAdapter

        paymentAdapter.onItemClickListener = { pos, clickOn ->
            if (clickOn == "addCard") {

            }
        }
    }
}