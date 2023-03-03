package com.live.emmazone.activities.provider

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.live.emmazone.adapter.ShopPostalCodesAdapter
import com.live.emmazone.databinding.ActivityShopPostalCodesBinding
import com.live.emmazone.extensionfuncton.getPreference
import com.live.emmazone.interfaces.OnPopupClick
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.CommonResponse
import com.live.emmazone.response_model.PostalCodesResponse
import com.live.emmazone.response_model.ShopDeliveryResponse
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.utils.AppUtils
import com.live.emmazone.utils.AppUtils.Companion.showToast
import com.live.emmazone.view_models.AppViewModel
import kotlinx.android.synthetic.main.activity_privacy_policy.*

class ShopPostalCodes : AppCompatActivity(), Observer<RestObservable> {
    private lateinit var binding: ActivityShopPostalCodesBinding
    private lateinit var adapter: ShopPostalCodesAdapter
    private val list = ArrayList<PostalCodesResponse.PostalCodeItem>()
    private val appViewModel: AppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopPostalCodesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        adapter = ShopPostalCodesAdapter(this, list) {
            AppUtils.showMsgOnlyWithClick(
                this,
                "Do you want to delete postal code : ${list[it].postalCode} ?",
                object : OnPopupClick {
                    override fun onPopupClickListener() {
                        deleteCode(it)
                    }
                })
        }
        binding.recyclerView.adapter = adapter
        binding.addPostalCode.setOnClickListener {
            validateAddData()
        }
        binding.back.setOnClickListener {
            finish()
        }
        getData()
    }

    private fun validateAddData() {
        val code = binding.enterPostalCode.text.toString().trim().toIntOrNull()
        if (code == null) {
            showToast("Please enter valid code")
            return
        }
        binding.enterPostalCode.setText("")
        addPostalCode(code)
    }

    private fun getData() {
        val map = HashMap<String, String>()
        map["vendorId"] = getPreference(AppConstants.VENDOR_ID, "")
        appViewModel.getShopPostalCodes(this, true, map)
        appViewModel.mResponse.observe(this, this)
    }

    private fun deleteCode(pos: Int) {
        val map = HashMap<String, String>()
        map["vendorId"] = getPreference(AppConstants.VENDOR_ID, "")
        map["postal_code"] = list[pos].postalCode.toString()
        appViewModel.deleteShopPostalCode(this, true, map)
        appViewModel.mResponse.observe(this, this)
    }

    private fun addPostalCode(code: Int) {
        val map = HashMap<String, String>()
        map["vendorId"] = getPreference(AppConstants.VENDOR_ID, "")
        map["postal_code"] = code.toString()
        appViewModel.addShopPostalCode(this, true, map)
        appViewModel.mResponse.observe(this, this)
    }

    override fun onChanged(t: RestObservable?) {
        when (t!!.status) {
            Status.SUCCESS -> {
                if (t.data is PostalCodesResponse) {
                    val response: PostalCodesResponse = t.data
                    list.clear()
                    list.addAll(response.body)
                    adapter.notifyDataSetChanged()
                    if (list.isEmpty()) {
                        binding.toHide.visibility = View.VISIBLE
                        binding.recyclerView.visibility = View.GONE
                    } else {
                        binding.toHide.visibility = View.GONE
                        binding.recyclerView.visibility = View.VISIBLE
                    }
                } else if (t.data is CommonResponse) {
                    val response: CommonResponse = t.data
                    showToast(response.message)
                    getData()
                }
            }
            Status.ERROR -> {
                if (t.data is PostalCodesResponse) {
                    val response: PostalCodesResponse = t.data
                    showToast(response.message)
                } else if (t.data is CommonResponse) {
                    val response: CommonResponse = t.data
                    showToast(response.message)
                }
            }
            else -> {}
        }
    }
}