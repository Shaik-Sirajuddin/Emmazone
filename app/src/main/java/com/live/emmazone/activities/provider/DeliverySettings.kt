package com.live.emmazone.activities.provider

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.live.emmazone.databinding.ActivityDeliverySettingsBinding
import com.live.emmazone.extensionfuncton.getPreference
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
        getData()
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
        appViewModel.getShopDelivery(this, true, map)
        appViewModel.mResponse.observe(this, this)
    }

    private fun apiHit() {
        val isLimitPrice = binding.limitPriceCheckbox.isChecked
        var limitPrice: Int? = null
        if (isLimitPrice) {
            val amount = binding.limitPrice.text.toString().toIntOrNull()
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

    private fun setData(data: ShopDeliveryResponse.Body) {
        Log.d("This", data.logistics_available.toString())
        binding.bicycleDelivery.isChecked = data.bicycle_available
        binding.selfDelivery.isChecked = data.shop_available
        if (data.limit_price == null) {
            binding.limitPriceCheckbox.isChecked = false
            switchVisibility(false)
        } else {
            binding.limitPriceCheckbox.isChecked = true
            binding.limitPrice.setText(data.limit_price.toString())
            switchVisibility(true)
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