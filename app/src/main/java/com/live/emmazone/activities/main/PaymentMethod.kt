package com.live.emmazone.activities.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.live.emmazone.R
import com.live.emmazone.adapter.AdapterAddPaymentCard
import com.live.emmazone.databinding.ActivityPaymentMethodBinding
import com.live.emmazone.model.ModelPaymentCard

class PaymentMethod : AppCompatActivity() {
    lateinit var binding: ActivityPaymentMethodBinding
    var list = ArrayList<ModelPaymentCard>()
    lateinit var adapter: AdapterAddPaymentCard
    var isNotification = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentMethodBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        binding.recyclerChooseCard.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        list.add(ModelPaymentCard(R.drawable.add_new_card))


        binding.recyclerChooseCard.adapter = AdapterAddPaymentCard(list)

    }
}