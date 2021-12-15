package com.live.emmazone.activities.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.live.emmazone.R
import com.live.emmazone.adapter.AdapterOrderDetail
import com.live.emmazone.databinding.ActivityReservedDeliveredDetailBinding
import com.live.emmazone.model.ModelOrderDetail

class ReservedDeliveredDetail : AppCompatActivity() {
    lateinit var binding : ActivityReservedDeliveredDetailBinding
    val list = ArrayList<ModelOrderDetail>()
    lateinit var adapter : AdapterOrderDetail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReservedDeliveredDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnQRScanner.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this)
            val factory = LayoutInflater.from(this)
            val view : View = factory.inflate(R.layout.dialog_scan_qr_code, null)

            val buttonCross= view.findViewById<ImageView>(R.id.crossImage)

            buttonCross.setOnClickListener {
                onBackPressed()
            }
            alertDialog.setView(view)
            alertDialog.show()
        }

        binding.back.setOnClickListener {
            onBackPressed()
        }

      binding.recyclerOrderDetail.layoutManager =  LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        list.add(ModelOrderDetail(R.drawable.shoes_square, "Brend Shoes",
            "3", "90.00€"))
        list.add(ModelOrderDetail(R.drawable.shoes_square, "Winter Sweeters",
            "1", "30.00€"))

        binding.recyclerOrderDetail.adapter = AdapterOrderDetail(list)

    }
}