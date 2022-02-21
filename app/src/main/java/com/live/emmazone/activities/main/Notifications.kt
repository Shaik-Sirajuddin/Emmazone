package com.live.emmazone.activities.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.live.emmazone.adapter.AdapterNotifications
import com.live.emmazone.databinding.ActivityNotificationsBinding
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.NotificatioListingResponse
import com.live.emmazone.view_models.AppViewModel

class Notifications : AppCompatActivity(), Observer<RestObservable> {

    private val appViewModel: AppViewModel by viewModels()

    lateinit var binding : ActivityNotificationsBinding
    var list = ArrayList<NotificatioListingResponse.Body>()
    lateinit var adapter: AdapterNotifications
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appViewModel.notificationListApi(this, true)
        appViewModel.getResponse().observe(this, this)

        binding.back.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onChanged(t: RestObservable?) {
        when (t!!.status) {
            Status.SUCCESS -> {
                if (t.data is NotificatioListingResponse) {
                    list.clear()
                    list.addAll(t.data.body)
                    if(list.size>0){
                        binding.tvNoNotification.visibility=View.GONE
                        binding.recyclerNotifications.visibility=View.VISIBLE
                        binding.recyclerNotifications.adapter = AdapterNotifications(list)
                    }else{
                        binding.tvNoNotification.visibility=View.VISIBLE
                        binding.recyclerNotifications.visibility=View.GONE
                    }


                }
            }
        }

    }
}