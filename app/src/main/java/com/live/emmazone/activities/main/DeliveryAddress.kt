package com.live.emmazone.activities.main

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.live.emmazone.R
import com.live.emmazone.activities.AddNewAddress
import com.live.emmazone.activities.listeners.OnActionListener
import com.live.emmazone.adapter.AdapterDeliveryAddress
import com.live.emmazone.databinding.ActivityDeliveryAddressBinding
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.AddressListResponse
import com.live.emmazone.response_model.DeleteAddressResponse
import com.live.emmazone.view_models.AppViewModel

class DeliveryAddress : AppCompatActivity(),Observer<RestObservable> {
    lateinit var binding: ActivityDeliveryAddressBinding

    private val appViewModel: AppViewModel by viewModels()

    var list = ArrayList<AddressListResponse.Body>()
    lateinit var addressAdapter: AdapterDeliveryAddress
    lateinit var recyclerView: RecyclerView
    var deletedPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeliveryAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnaddNewAddress.setOnClickListener {
            val intent = Intent(this, AddNewAddress::class.java)
            resultLauncher.launch(intent)
        }

        binding.btnNext.setOnClickListener {
            onBackPressed()
        }
        binding.back.setOnClickListener {
            onBackPressed()
        }

        appViewModel.addressListApi(this, true)
        appViewModel.getResponse().observe(this, this)


        recyclerView = findViewById(R.id.recyclerDeliveryAddress)
//
//        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//
//        list.add(ModelDeliveryAddress("John Marker", "260-C North EI Camino Real", isSelected = true))
//
//        list.add(ModelDeliveryAddress("Jackson", "1186 Roseville pkwy"))
//
//        list.add(ModelDeliveryAddress("John Marker", "260-C North EI Camino Real"))
//

    }

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            appViewModel.addressListApi(this, true)
        }
    }


    override fun onChanged(t: RestObservable?) {

        when (t!!.status) {
            Status.SUCCESS -> {
                if (t.data is AddressListResponse) {
                    val response: AddressListResponse = t.data

                    list.clear()
                    list.addAll(response.body)

                    val onActionListener = object : OnActionListener<AddressListResponse.Body> {
                        override fun notify(model: AddressListResponse.Body, position: Int) {
                            for ((i, item) in list.withIndex()) {
                                item.isSelected = (position == i)
                                addressAdapter.notifyDataSetChanged()
                            }
                        }
                    }
                    addressAdapter = AdapterDeliveryAddress(list, onActionListener)
                    binding.recyclerDeliveryAddress.adapter = addressAdapter

                    addressAdapter.onClickListener = { pos ->
                        deletedPosition = pos
                        var id = list[pos].id
                        val hashMap = HashMap<String,String>()
                        hashMap["id"] = id.toString()
                        appViewModel.deleteAddressApi(this, true,hashMap)

                    }

                }

                else if (t.data is DeleteAddressResponse){
                   list.removeAt(deletedPosition)
                    addressAdapter.notifyDataSetChanged()

                }
            }
        }
    }
}