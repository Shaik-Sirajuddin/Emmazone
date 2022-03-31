package com.live.emmazone.activities.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.live.emmazone.MainActivity
import com.live.emmazone.activities.provider.ProviderMainActivity
import com.live.emmazone.adapter.AdapterNotifications
import com.live.emmazone.databinding.ActivityNotificationsBinding
import com.live.emmazone.extensionfuncton.getPreference
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.NotificationListingResponse
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.view_models.AppViewModel

class Notifications : AppCompatActivity(), Observer<RestObservable> {

    private val appViewModel: AppViewModel by viewModels()

    lateinit var binding: ActivityNotificationsBinding
    var list = ArrayList<NotificationListingResponse.Body>()
    lateinit var notificationAdapter: AdapterNotifications
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

        setNotificationAdapter()
    }

    private fun setNotificationAdapter() {
        notificationAdapter = AdapterNotifications(list)
        binding.recyclerNotifications.adapter = notificationAdapter

        notificationAdapter.onItemClick = { pos: Int ->
            if (getPreference(AppConstants.ROLE, "") == AppConstants.USER_ROLE) {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra(AppConstants.NOTIFICATION_RESPONSE, list[pos])
                startActivity(intent)
            } else if (getPreference(AppConstants.ROLE, "") == AppConstants.SELLER_ROLE) {
                val intent = Intent(this, ProviderMainActivity::class.java)
                intent.putExtra(AppConstants.NOTIFICATION_RESPONSE, list[pos])
                startActivity(intent)
            }
        }

    }

    override fun onChanged(t: RestObservable?) {
        when (t!!.status) {
            Status.SUCCESS -> {
                if (t.data is NotificationListingResponse) {
                    list.clear()
                    list.addAll(t.data.body)

                    notificationAdapter.notifyDataSetChanged()

                    if (list.size > 0) {
                        binding.tvNoNotification.visibility = View.GONE
                        binding.recyclerNotifications.visibility = View.VISIBLE
                    } else {
                        binding.tvNoNotification.visibility = View.VISIBLE
                        binding.recyclerNotifications.visibility = View.GONE
                    }

                }
            }
        }

    }
}