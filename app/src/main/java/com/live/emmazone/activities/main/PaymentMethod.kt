package com.live.emmazone.activities.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.live.emmazone.activities.AddCardActivity
import com.live.emmazone.adapter.AdapterAddPaymentCard
import com.live.emmazone.databinding.ActivityPaymentMethodBinding
import com.live.emmazone.extensionfuncton.Validator
import com.live.emmazone.extensionfuncton.getPreference
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.CardListResponse
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.utils.AppUtils
import com.live.emmazone.view_models.AppViewModel

class PaymentMethod : AppCompatActivity(), Observer<RestObservable> {

    var cardId = ""
    var cardCVV: String? = ""

    private val appViewModel: AppViewModel by viewModels()
    private lateinit var binding: ActivityPaymentMethodBinding
    private var addPaymentAdapter: AdapterAddPaymentCard? = null
    private val cardList = ArrayList<CardListResponse.Body>()

    private var selectedPaymentType = "" //0=>Wallet 1=>Card 2=>cash 3=>PayPal

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
        getSelectedType()
    }

    private fun getSelectedType() {
        selectedPaymentType = getPreference(AppConstants.PAYMENT_TYPE, "")
        cardId = getPreference(AppConstants.SAVED_CARD_ID, "")
        cardCVV = getPreference(AppConstants.SAVED_CVV, "")

        if (selectedPaymentType.isNotEmpty()) {

            if (selectedPaymentType == "0") {
                binding.rbWallet.isChecked = true
            } else if (selectedPaymentType == "1") {
                binding.rbCredit.isChecked = true
                binding.tvChooseCard.visibility = View.VISIBLE
                binding.recyclerChooseCard.visibility = View.VISIBLE
            } else if (selectedPaymentType == "2") {
                binding.rbCOD.isChecked = true
            } else if (selectedPaymentType == "3") {
                binding.rbPayPal.isChecked = true
            }
        }

    }

    private fun clicksHandle() {
        binding.rbWallet.setOnClickListener {
            if (binding.rbWallet.isChecked) {
                selectedPaymentType = "0"
                binding.rbPayPal.isChecked = false
                binding.rbCredit.isChecked = false
                binding.rbCOD.isChecked = false
                binding.tvChooseCard.visibility = View.GONE
                binding.recyclerChooseCard.visibility = View.GONE
            }
        }

        binding.rbPayPal.setOnClickListener {
            if (binding.rbPayPal.isChecked) {
                selectedPaymentType = "3"
                binding.rbWallet.isChecked = false
                binding.tvChooseCard.visibility = View.GONE
                binding.recyclerChooseCard.visibility = View.GONE
            }
        }

        binding.rbCredit.setOnClickListener {
            if (binding.rbCredit.isChecked) {
                selectedPaymentType = "1"
                binding.rbWallet.isChecked = false
                binding.tvChooseCard.visibility = View.VISIBLE
                binding.recyclerChooseCard.visibility = View.VISIBLE
            }
        }

        binding.rbCOD.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                selectedPaymentType = "2"
                binding.rbWallet.isChecked = false
                binding.tvChooseCard.visibility = View.GONE
                binding.recyclerChooseCard.visibility = View.GONE
            }
        }


        binding.back.setOnClickListener {
            onBackPressed()
        }

        binding.btnNext.setOnClickListener {
            if (selectedPaymentType == "2") {
                val intent = Intent()
                intent.putExtra("paymentType", selectedPaymentType)
                intent.putExtra("cardId", cardId)
                intent.putExtra("cardCVV", cardCVV)

                setResult(RESULT_OK, intent)
                onBackPressed()
            } else if (selectedPaymentType == "1") {
                validateData()
            }
        }
    }

    private fun validateData() {

        cardList.forEach {
            if (it.isSelected) {
                cardId = it.id.toString()
                cardCVV = it.cvvCode
            }
        }


        if (Validator.selectCard(selectedPaymentType, cardId, cardCVV)) {

            val intent = Intent()
            intent.putExtra("paymentType", selectedPaymentType)
            intent.putExtra("cardId", cardId)
            intent.putExtra("cardCVV", cardCVV)

            setResult(RESULT_OK, intent)
            onBackPressed()
        } else {
            AppUtils.showMsgOnlyWithoutClick(this, Validator.errorMessage)
        }

    }

    private fun setPaymentAdapter() {
        addPaymentAdapter = AdapterAddPaymentCard(cardList)
        binding.recyclerChooseCard.adapter = addPaymentAdapter

        addPaymentAdapter?.onItemClickListener = { pos, clickOn ->
            if (clickOn == "addCard") {
                val intent = Intent(this, AddCardActivity::class.java)
                launcher.launch(intent)
            } else if (clickOn == "selectCard") {
                cardList.forEachIndexed { index, body ->
                    body.isSelected = index == pos
                }

                addPaymentAdapter?.notifyDataSetChanged()
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

                        if (selectedPaymentType.isNotEmpty() && selectedPaymentType == "1") {
                            cardList.forEach {
                                if (it.id.toString() == cardId) {
                                    it.cvvCode = cardCVV.toString()
                                    it.isSelected = true
                                }
                            }
                        }

                        addPaymentAdapter?.notifyDataSetChanged()
                    }
                }
            }
        }
    }
}