package com.live.emmazone.activities.provider

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.live.emmazone.databinding.ActivityDeliverySettingsBinding
import com.live.emmazone.extensionfuncton.getPreference
import com.live.emmazone.model.ShopDeliveryModel
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.CommonResponse
import com.live.emmazone.response_model.ShopDeliveryResponse
import com.live.emmazone.utils.App
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.utils.AppUtils
import com.live.emmazone.utils.AppUtils.Companion.showToast
import com.live.emmazone.view_models.AppViewModel
import com.schunts.extensionfuncton.toBody
import com.schunts.extensionfuncton.toast
import okhttp3.RequestBody

class DeliverySettings : AppCompatActivity(), Observer<RestObservable> {
    private lateinit var binding: ActivityDeliverySettingsBinding
    private val appViewModel: AppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeliverySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        binding.limitPriceCheckbox.setOnCheckedChangeListener { compoundButton, checked ->
            switchVisibility(checked)
        }
        binding.saveButton.setOnClickListener {
            apiHit()
        }
        binding.managePostalCodes.setOnClickListener {
            val intent = Intent(this, ShopPostalCodes::class.java)
            startActivity(intent)
        }
        binding.manageDeliveryTemplates.setOnClickListener {
            val intent = Intent(this, DeliveryPricingTemplatesActivity::class.java)
            startActivity(intent)
        }
        binding.back.setOnClickListener {
            finish()
        }
        binding.limitPrice.addTextChangedListener(MyTextWatcher(binding.limitPrice))
        getData()
    }

    inner class MyTextWatcher(private val editText: EditText) : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(e: Editable) {
            if (editText.tag == "this") return
            val text = editText.text.toString().replace(".", "").trim()
            val priceText = text.replace(",", ".")
            var price = priceText.toDoubleOrNull()
                ?: return
            Log.e("price", price.toString())
            editText.tag = "this"
            price *= 100
            val res = price.toInt()
            price = res.toDouble() / 100
            var formattedText = AppUtils.getFormattedAmountForEdit(price)
            if (text.contains(',')) {

                val comma = text.indexOf(',')
                var afterText = ""
                if (comma != text.lastIndex) {
                    afterText = text.substring(comma)
                    if (afterText.length > 3) {
                        afterText = afterText.substring(0, 3)
                    }
                    if (afterText == ",0" || afterText == ",00") {
                        formattedText = "$formattedText$afterText"
                    } else if (afterText.length == 3 && afterText[2] == '0') {
                        formattedText = "${formattedText}0"
                    }
                } else {
                    formattedText = "$formattedText,"
                }

            }

            editText.setText(formattedText)

            editText.tag = null
            editText.setSelection(editText.length())
        }

    }

    private fun switchVisibility(checked: Boolean) {
        if (checked) {
            binding.freeOrderInternalLayout.visibility = View.VISIBLE
        } else {
            binding.freeOrderInternalLayout.visibility = View.GONE
        }
    }

    private fun getData() {
        val map = HashMap<String, String>()
        map["vendorId"] = getPreference(AppConstants.VENDOR_ID, "")
        map["checkService"] = true.toString()
        appViewModel.getShopDelivery(this, true, map)
        appViewModel.mResponse.observe(this, this)
    }

    private fun priceStringToDouble(productPrice: String): Double? {
        val text = productPrice.replace(".", "").trim()
        val priceText = text.replace(",", ".")
        return priceText.toDoubleOrNull()
    }

    private fun priceStringToShow(productPrice: String): String {
        return productPrice.replace(".", ",")
    }

    private fun apiHit() {
        val isLimitPrice = binding.limitPriceCheckbox.isChecked
        var limitPrice: Double? = null
        if (isLimitPrice) {
            val amountText = binding.limitPrice.text.toString()
            val amount = priceStringToDouble(amountText)
            if (amount == null) {
                toast("Enter valid amount")
                return
            }
            limitPrice = amount
        }
        val map = HashMap<String, String>()
        map["bicycle_available"] = binding.bicycleDelivery.isChecked.toString()
        map["shop_available"] = binding.selfDelivery.isChecked.toString()
        map["logistics_available"] = binding.thirdPartyDelivery.isChecked.toString()
        map["limit_price"] = limitPrice.toString()
        map["vendorId"] = getPreference(AppConstants.VENDOR_ID, "")
        appViewModel.editShopDelivery(this, true, map)
        appViewModel.mResponse.observe(this, this)
    }

    private fun setData(data: ShopDeliveryModel) {
        Log.d("This", data.logistics_available.toString())
        binding.bicycleDelivery.isChecked = data.bicycle_available
        binding.selfDelivery.isChecked = data.shop_available
        if (data.limit_price == null) {
            binding.limitPriceCheckbox.isChecked = false
            switchVisibility(false)
        } else {
            binding.limitPriceCheckbox.isChecked = true
            binding.limitPrice.setText(priceStringToShow(data.limit_price))
            switchVisibility(true)
        }
        if (data.serviceable) {
            binding.bicycleCard.visibility = View.VISIBLE
        } else {
            binding.bicycleCard.visibility = View.GONE
        }
    }

    override fun onChanged(t: RestObservable?) {
        when (t!!.status) {
            Status.SUCCESS -> {
                if (t.data is CommonResponse) {
                    val response: CommonResponse = t.data
                    showToast(response.message)
                    finish()
                } else if (t.data is ShopDeliveryResponse) {
                    val response: ShopDeliveryResponse = t.data
                    setData(response.body)
                }
            }
            Status.ERROR -> {
                if (t.data is CommonResponse) {
                    val response: CommonResponse = t.data
                    showToast(response.message)
                }
            }
            else -> {}
        }
    }

}