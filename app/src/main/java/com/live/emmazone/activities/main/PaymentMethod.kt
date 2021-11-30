package com.live.emmazone.activities.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.live.emmazone.R
import com.live.emmazone.adapter.AdapterAddPaymentCard
import com.live.emmazone.adapter.AdapterShopDetailCategory
import com.live.emmazone.databinding.ActivityPaymentMethodBinding
import com.live.emmazone.model.ModelPaymentCard
import com.live.emmazone.model.ModelShopDetailCategory
import com.live.emmazone.model.ModelShopDetailProducts

class PaymentMethod : AppCompatActivity() {
    lateinit var binding : ActivityPaymentMethodBinding
    var list = ArrayList<ModelPaymentCard>()
    lateinit var adapter: AdapterAddPaymentCard

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentMethodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        binding.recyclerChooseCard.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        list.add(ModelPaymentCard(R.drawable.add_new_card))
        list.add(ModelPaymentCard(R.drawable.add_new_card))

        binding.recyclerChooseCard.adapter = AdapterAddPaymentCard(list)

    }
}