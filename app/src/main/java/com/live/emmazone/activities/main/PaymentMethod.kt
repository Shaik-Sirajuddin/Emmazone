package com.live.emmazone.activities.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.live.emmazone.activities.AddCardActivity
import com.live.emmazone.adapter.AdapterAddPaymentCard
import com.live.emmazone.databinding.ActivityPaymentMethodBinding
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.CardListResponse
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.view_models.AppViewModel

class PaymentMethod : AppCompatActivity(), Observer<RestObservable> {

    private val appViewModel: AppViewModel by viewModels()
    private lateinit var binding: ActivityPaymentMethodBinding
    private var addPaymentAdapter: AdapterAddPaymentCard? = null
    private val cardList = ArrayList<CardListResponse.Body>()


    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                getCardListApiHit()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentMethodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clicksHandle()
        setPaymentAdapter()
        getCardListApiHit()

    }

    private fun clicksHandle() {
        binding.rbWallet.setOnClickListener {
            if (binding.rbWallet.isChecked) {
                binding.rbPayPal.isChecked = false
                binding.rbCredit.isChecked = false
                binding.rbCOD.isChecked = false
            }
        }

        binding.rbPayPal.setOnClickListener {
            if (binding.rbPayPal.isChecked) {
                binding.rbWallet.isChecked = false
            }
        }

        binding.rbCredit.setOnClickListener {
            if (binding.rbCredit.isChecked) {
                binding.rbWallet.isChecked = false
            }
        }

        binding.rbCOD.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                binding.rbWallet.isChecked = false
            }
        }


        binding.back.setOnClickListener {
            onBackPressed()
        }

        binding.btnNext.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setPaymentAdapter() {
        addPaymentAdapter = AdapterAddPaymentCard(cardList)
        binding.recyclerChooseCard.adapter = addPaymentAdapter

        addPaymentAdapter?.onItemClickListener = { pos, clickOn ->
            if (clickOn == "addCard") {
                val intent = Intent(this, AddCardActivity::class.java)
                launcher.launch(intent)
            }
        }
    }


    private fun getCardListApiHit() {
        appViewModel.cardListApi(this, true)
        appViewModel.getResponse().observe(this, this)
    }

    override fun onChanged(t: RestObservable?) {
        when (t!!.status) {
            Status.SUCCESS -> {
                if (t.data is CardListResponse) {
                    val response: CardListResponse = t.data

                    if (response.code == AppConstants.SUCCESS_CODE) {

                        cardList.clear()
                        cardList.addAll(response.body)
                        addPaymentAdapter?.notifyDataSetChanged()
                    }
                }
            }
        }
    }
}