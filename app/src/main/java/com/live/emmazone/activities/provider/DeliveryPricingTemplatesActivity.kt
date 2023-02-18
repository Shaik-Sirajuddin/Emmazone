package com.live.emmazone.activities.provider

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.live.emmazone.R
import com.live.emmazone.databinding.ActivityDeliveryPricingTemplatesBinding
import com.live.emmazone.extensionfuncton.getPreference
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.CommonResponse
import com.live.emmazone.response_model.DeliveryTemplateResponse
import com.live.emmazone.response_model.ShopDeliveryResponse
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.utils.AppUtils.Companion.showToast
import com.live.emmazone.view_models.AppViewModel

class DeliveryPricingTemplatesActivity : AppCompatActivity(), Observer<RestObservable> {

    private lateinit var binding: ActivityDeliveryPricingTemplatesBinding
    private var templateNo = 0
    private val appViewModel: AppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeliveryPricingTemplatesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        binding.back.setOnClickListener {
            finish()
        }
        /* Setting adapter configuration */
        val templateNames = resources.getStringArray(R.array.delivery_template_names)
        // create an array adapter and pass the required parameter
        // in our case pass the context, drop down layout , and array.
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, templateNames)
        // get reference to the autocomplete text view
        binding.autoCompleteTextView.setAdapter(adapter)
        binding.autoCompleteTextView.setDropDownBackgroundResource(R.color.white)
        binding.autoCompleteTextView.setOnItemClickListener { adapterView, view, i, l ->
            templateNo = i
            loadTemplate()
        }
        binding.autoCompleteTextView.setText(templateNames[templateNo],false)
        binding.updateButton.setOnClickListener {
            update()
        }
        loadTemplate()
    }

    private fun loadTemplate() {
        val map = HashMap<String, String>()
        map["vendorId"] = getPreference(AppConstants.VENDOR_ID, "")
        map["templateNo"] = templateNo.toString()
        appViewModel.getDeliveryTemplate(this, true, map)
        appViewModel.mResponse.observe(this, this)
    }

    private fun update() {
        val shopPricing = binding.shopPricing.text.toString().trim().toIntOrNull()
        val bicyclePricing = binding.bicyclePricing.text.toString().trim().toIntOrNull()
        val thirdPartyPricing = binding.thirdPartyPricing.text.toString().trim().toIntOrNull()
        if (shopPricing == null || bicyclePricing == null || thirdPartyPricing == null) {
            showToast("Please fill all fields")
            return
        }
        val map = HashMap<String, String>()
        map["bicycle_price"] = bicyclePricing.toString()
        map["shop_price"] = shopPricing.toString()
        map["logistics_price"] = thirdPartyPricing.toString()
        map["vendorId"] = getPreference(AppConstants.VENDOR_ID, "")
        map["templateNo"] = templateNo.toString()
        appViewModel.editDeliveryTemplate(this, true, map)
        appViewModel.mResponse.observe(this, this)
    }

    private fun setData(body: DeliveryTemplateResponse.Body) {
        binding.bicyclePricing.setText(body.bicycle_price.toString())
        binding.shopPricing.setText(body.shop_price.toString())
        binding.thirdPartyPricing.setText(body.logistics_price.toString())
    }

    override fun onChanged(t: RestObservable?) {
        when (t!!.status) {
            Status.SUCCESS -> {
                if (t.data is CommonResponse) {
                    val response: CommonResponse = t.data
                    showToast(response.message)
                } else if (t.data is DeliveryTemplateResponse) {
                    val response: DeliveryTemplateResponse = t.data
                    setData(response.body)
                }
            }
            Status.ERROR -> {
                if (t.data is CommonResponse) {
                    showToast(t.data.message)
                } else if (t.data is DeliveryTemplateResponse) {
                    showToast(t.data.message)
                }
            }
            else -> {}
        }
    }

}