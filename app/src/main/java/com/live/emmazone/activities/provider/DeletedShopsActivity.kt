package com.live.emmazone.activities.provider

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.live.emmazone.adapter.AdapterDeletedShops
import com.live.emmazone.databinding.ActivityDeletedShopsBinding
import com.live.emmazone.model.DeletedShopsModel
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.CommonResponse
import com.live.emmazone.response_model.DeletedShopsResponse
import com.live.emmazone.utils.AppUtils.Companion.showToast
import com.live.emmazone.view_models.AppViewModel

class DeletedShopsActivity : AppCompatActivity(), Observer<RestObservable> {
    private lateinit var binding: ActivityDeletedShopsBinding
    private lateinit var adapter: AdapterDeletedShops
    private val list = ArrayList<DeletedShopsModel>()
    private val appViewModel: AppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeletedShopsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        adapter = AdapterDeletedShops(this, list) {
            deleteShopApiHit(it)
        }
        getDataApiHit()
        binding.recyclerView.adapter = adapter
        binding.back.setOnClickListener {
            finish()
        }
    }

    private fun getDataApiHit() {
        appViewModel.getDeletedShops(this, true)
        appViewModel.getResponse().observe(this, this)
    }

    private fun deleteShopApiHit(pos: Int) {
        val hashMap = HashMap<String, String>()
        hashMap["userId"] = list[pos].userId.toString()
        appViewModel.confirmDeleteShopProfile(this, true, hashMap)
        appViewModel.getResponse().observe(this, this)
    }

    override fun onChanged(t: RestObservable?) {
        when (t!!.status) {
            Status.SUCCESS -> {
                if (t.data is DeletedShopsResponse) {
                    list.clear()
                    list.addAll(t.data.body)
                    adapter.notifyDataSetChanged()
                    if (list.isEmpty()) {
                        binding.noDataText.visibility = View.VISIBLE
                    } else {
                        binding.noDataText.visibility = View.GONE
                    }
                } else if (t.data is CommonResponse) {
                    showToast(t.data.message)
                    getDataApiHit()
                }
            }
            Status.ERROR -> {
                if (t.data is DeletedShopsResponse) {
                    showToast(t.data.message)
                } else if (t.data is CommonResponse) {
                    showToast(t.data.message)
                }
            }
            else -> {}
        }
    }
}