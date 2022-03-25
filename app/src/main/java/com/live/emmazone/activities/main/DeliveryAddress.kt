package com.live.emmazone.activities.main

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.live.emmazone.R
import com.live.emmazone.activities.AddNewAddress
import com.live.emmazone.activities.listeners.OnActionListener
import com.live.emmazone.adapter.AdapterDeliveryAddress
import com.live.emmazone.databinding.ActivityDeliveryAddressBinding
import com.live.emmazone.extensionfuncton.getPrefObject
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.AddressListResponse
import com.live.emmazone.response_model.DeleteAddressResponse
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.utils.AppUtils
import com.live.emmazone.view_models.AppViewModel

class DeliveryAddress : AppCompatActivity(), Observer<RestObservable> {
    lateinit var binding: ActivityDeliveryAddressBinding

    private val appViewModel: AppViewModel by viewModels()

    var list = ArrayList<AddressListResponse.Body>()
    lateinit var addressAdapter: AdapterDeliveryAddress
    var deletedPosition = 0

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                appViewModel.addressListApi(this, true)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeliveryAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)



        appViewModel.addressListApi(this, true)
        appViewModel.getResponse().observe(this, this)

        clicksHandle()

    }

    private fun clicksHandle() {
        binding.btnaddNewAddress.setOnClickListener {
            val intent = Intent(this, AddNewAddress::class.java)
            resultLauncher.launch(intent)
        }

        binding.btnNext.setOnClickListener {
            validateData()
        }

        binding.back.setOnClickListener {
            onBackPressed()
        }
    }

    private fun validateData() {
        var addressResponse: AddressListResponse.Body? = null
        list.forEach {
            if (it.isSelected) {
                addressResponse = it
            }
        }

        if (addressResponse != null) {
            setResult(
                RESULT_OK,
                intent.putExtra(AppConstants.Address_LIST_RESPONSE, addressResponse)
            )
            finish()
        } else {
            AppUtils.showMsgOnlyWithoutClick(this,getString(R.string.pls_select_address))
        }

    }

    private fun setAddressAdapter() {
        noDataVisibility()
        addressAdapter = AdapterDeliveryAddress(list)
        binding.recyclerDeliveryAddress.adapter = addressAdapter

        addressAdapter.onClickListener = { pos, clickOn ->
            if (clickOn == "delete") {
                deletedPosition = pos
                deleteAddressApiHit(list[pos].id)
            } else if (clickOn == "item") {
                list.forEachIndexed { index, body ->
                    body.isSelected = index == pos
                }
                addressAdapter.notifyDataSetChanged()
            }

        }
    }

    private fun deleteAddressApiHit(id: Int) {
        val hashMap = HashMap<String, String>()
        hashMap["id"] = id.toString()
        appViewModel.deleteAddressApi(this, true, hashMap)
    }


    private fun noDataVisibility() {
        if (list.isEmpty()) {
            binding.tvNoData.visibility = View.VISIBLE
            binding.btnNext.visibility=View.GONE
            binding.recyclerDeliveryAddress.visibility = View.GONE
        } else {
            binding.btnNext.visibility=View.VISIBLE
            binding.tvNoData.visibility = View.GONE
            binding.recyclerDeliveryAddress.visibility = View.VISIBLE
        }
    }

    override fun onChanged(t: RestObservable?) {

        when (t!!.status) {
            Status.SUCCESS -> {
                if (t.data is AddressListResponse) {
                    val response: AddressListResponse = t.data
                    list.clear()
                    list.addAll(response.body)

                    val savedAddress: AddressListResponse.Body? =
                        getPrefObject(AppConstants.SAVED_ADDRESS_RESPONSE) as AddressListResponse.Body

                    if (savedAddress != null) {
                        list.forEach {
                            it.isSelected = it.id == savedAddress.id
                        }
                    }


                    setAddressAdapter()

                } else if (t.data is DeleteAddressResponse) {
                    list.removeAt(deletedPosition)
                    addressAdapter.notifyDataSetChanged()
                    noDataVisibility()

                }
            }
        }
    }


}