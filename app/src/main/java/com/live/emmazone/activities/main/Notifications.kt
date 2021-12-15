package com.live.emmazone.activities.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.live.emmazone.R
import com.live.emmazone.adapter.AdapterDeliveryAddress
import com.live.emmazone.adapter.AdapterNotifications
import com.live.emmazone.databinding.ActivityNotificationsBinding
import com.live.emmazone.model.ModelDeliveryAddress
import com.live.emmazone.model.ModelNotifications

class Notifications : AppCompatActivity() {
    lateinit var binding : ActivityNotificationsBinding
    var list = ArrayList<ModelNotifications>()
    lateinit var adapter: AdapterNotifications
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener {
            onBackPressed()
        }

       // recyclerView = findViewById(R.id.recyclerNotifications)

       binding.recyclerNotifications.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        list.add(ModelNotifications(R.drawable.nikk, "Jolly Deo",
            "Lorem ipsum is simply dummy text of the printing and typesetting industry",
        "10:45AM"))
        list.add(ModelNotifications(R.drawable.nick2, "Merry Karker",
            "Lorem ipsum is simply dummy text of the printing and typesetting industry",
            "10:45AM"))
        list.add(ModelNotifications(R.drawable.nick3, "John Marker",
            "Lorem ipsum is simply dummy text of the printing and typesetting industry",
            "10:45AM"))
        list.add(ModelNotifications(R.drawable.nikki, "Nikkie Jael",
            "Lorem ipsum is simply dummy text of the printing and typesetting industry",
            "10:45AM"))

        binding.recyclerNotifications.adapter = AdapterNotifications(list)

    }
}