package com.live.emmazone.activities.provider

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.live.emmazone.R
import com.live.emmazone.databinding.ActivityDeliveryPricingTemplatesBinding
import com.live.emmazone.extensionfuncton.getPreference
import com.live.emmazone.model.DeliveryTemplateModel
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.CommonResponse
import com.live.emmazone.response_model.DeliveryTemplateResponse
import com.live.emmazone.response_model.ShopDeliveryResponse
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.utils.AppUtils
import com.live.emmazone.utils.AppUtils.Companion.showToast
import com.live.emmazone.view_models.AppViewModel

class DeliveryPricingTemplatesActivity : AppCompatActivity(), Observer<RestObservable> {

    private lateinit var binding: ActivityDeliveryPricingTemplatesBinding
    private var selectedIndex = -1
    private val appViewModel: AppViewModel by viewModels()
    private lateinit var adapter: ArrayAdapter<String>
    private val templates = ArrayList<DeliveryTemplateModel>()
    private val templateNames = ArrayList<String>()
    val TEMPLATE_ADDED = "Delivery template added"
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

        binding.addTemplate.setOnClickListener {
            if (binding.cardView1.visibility == View.VISIBLE) {
                binding.cardView1.visibility = View.GONE
            } else {
                binding.cardView1.visibility = View.VISIBLE
            }
        }
        binding.addTemplateButton.setOnClickListener {
            addTemplate()
        }
        /** Start : Setting up autoCompleteTextview configuration */
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, templateNames)
        binding.autoCompleteTextView.setAdapter(adapter)
        binding.autoCompleteTextView.setDropDownBackgroundResource(R.color.white)
        binding.autoCompleteTextView.setOnItemClickListener { adapterView, view, i, l ->
            selectedIndex = i
            loadData()
        }
        /**End*/


        binding.updateButton.setOnClickListener {
            update()
        }

        binding.shopPricing.addTextChangedListener(MyTextWatcher(binding.shopPricing))
        binding.bicyclePricing.addTextChangedListener(MyTextWatcher(binding.bicyclePricing))
        binding.thirdPartyPricing.addTextChangedListener(MyTextWatcher(binding.thirdPartyPricing))

        loadTemplates()
    }

    private fun loadData() {
        val body = templates[selectedIndex]
        binding.bicyclePricing.setText(priceStringToShow(body.bicycle_price.toString()))
        binding.shopPricing.setText(priceStringToShow(body.shop_price.toString()))
        binding.thirdPartyPricing.setText(priceStringToShow(body.logistics_price.toString()))
    }

    private fun loadTemplates() {
        val map = HashMap<String, String>()
        map["vendorId"] = getPreference(AppConstants.VENDOR_ID, "")
        appViewModel.getDeliveryTemplates(this, true, map)
        appViewModel.mResponse.observe(this, this)
    }

    private fun addTemplate() {
        val templateName = binding.newTemplateName.text.toString().trim()
        if (templateName.isEmpty()) {
            showToast("Please enter a valid template name")
            return
        }
        val map = HashMap<String, String>()
        map["vendorId"] = getPreference(AppConstants.VENDOR_ID, "")
        map["templateName"] = templateName
        appViewModel.addDeliveryTemplate(this, true, map)
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

    private fun update() {
        val shopPricing = priceStringToDouble(binding.shopPricing.text.toString().trim())
        val bicyclePricing = priceStringToDouble(binding.bicyclePricing.text.toString().trim())
        val thirdPartyPricing =
            priceStringToDouble(binding.thirdPartyPricing.text.toString().trim())
        if (shopPricing == null || bicyclePricing == null || thirdPartyPricing == null) {
            showToast("Please fill all fields")
            return
        }
        val map = HashMap<String, String>()
        map["bicycle_price"] = bicyclePricing.toString()
        map["shop_price"] = shopPricing.toString()
        map["logistics_price"] = thirdPartyPricing.toString()
        map["vendorId"] = getPreference(AppConstants.VENDOR_ID, "")
        map["id"] = templates[selectedIndex].id.toString()
        appViewModel.editDeliveryTemplate(this, true, map)
        appViewModel.mResponse.observe(this, this)
    }

    private fun setData(body: List<DeliveryTemplateModel>) {
        templates.clear()
        templates.addAll(body)
        templateNames.clear()
        templates.forEach {
            templateNames.add(it.templateName)
        }
        adapter.notifyDataSetChanged()
    }

    override fun onChanged(t: RestObservable?) {
        when (t!!.status) {
            Status.SUCCESS -> {
                if (t.data is CommonResponse) {
                    val response: CommonResponse = t.data
                    showToast(response.message)
                    if (response.message == TEMPLATE_ADDED) {
                        binding.newTemplateName.setText("")
                    }
                    loadTemplates()
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

}