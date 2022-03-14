package com.live.emmazone.activities.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.live.emmazone.R
import com.live.emmazone.adapter.AdapterOrderDetail
import com.live.emmazone.databinding.ActivityReservedDeliveredDetailBinding
import com.live.emmazone.response_model.UserOrderListing
import com.live.emmazone.utils.AppUtils

class ReservedDeliveredDetail : AppCompatActivity() {
    lateinit var binding : ActivityReservedDeliveredDetailBinding
    val list = ArrayList<UserOrderListing.OrderListBody.OrderJson.OrderItem>()
    lateinit var adapter : AdapterOrderDetail
    lateinit var binding: ActivityReservedDeliveredDetailBinding
    val list = ArrayList<UserOrderListing.Body.Response.OrderJson.OrderItem>()
    lateinit var adapter: AdapterOrderDetail

    var userData: UserOrderListing.OrderListBody?=null

    var userData: UserOrderListing.Body.Response? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReservedDeliveredDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userData = intent.extras!!.get("data") as UserOrderListing.Body.Response

        setData(userData!!)

        userData= intent.extras!!.get("data") as UserOrderListing.OrderListBody

        setData(userData!!)

        binding.btnQRScanner.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this)
            val factory = LayoutInflater.from(this)
            val view: View = factory.inflate(R.layout.dialog_scan_qr_code, null)

            val buttonCross = view.findViewById<ImageView>(R.id.crossImage)

            buttonCross.setOnClickListener {
                onBackPressed()
            }
            alertDialog.setView(view)
            alertDialog.show()
        }

        binding.back.setOnClickListener {
            onBackPressed()
        }

        binding.recyclerOrderDetail.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        binding.recyclerOrderDetail.adapter = AdapterOrderDetail(this,list)
        binding.recyclerOrderDetail.adapter = AdapterOrderDetail(this, list)

    }
    }

    private fun setData(data: UserOrderListing.Body.Response) {
        list.addAll(data.orderJson.orderItems)
        adapter?.notifyDataSetChanged()
        binding.tvOrderID.text = data.orderNo
        binding.tvSubTotalPrice.text = data.netAmount
        binding.tvDeliveryChargesPrice.text = data.shippingCharges
        binding.tvTaxPrice.text = data.taxCharged
        binding.tvTotalPrice.text = data.total
        binding.tvODOrderDate.text = AppUtils.getDateTime(data.created.toLong())
        data.orderJson.userAddress.apply {
            binding.tvOrderPersonName.text = this.name
            binding.tvOrderDeliveryAddress.text =
                this.address + "," + this.city + "," + this.state + "," + this.zipcode
        }
        when (data.orderStatus) {
            0 -> { //  order status  0-> Pending  1-> on the way 2-> Delivered 3-> cancelled
                binding.tvOrderStatus.text = getString(R.string.pending)
            }
            1 -> {
                binding.tvOrderStatus.text = getString(R.string.on_the_way)
            }
            2 -> {
                binding.tvOrderStatus.text = getString(R.string.delivered)
            }
        }
        when (data.deliveryType) {
            0 -> {   //   0-> click & collect 1-> Lifernado 2-> Own Delivery
                binding.tvPaymentType.text = getString(R.string.click_and_collect)
            }
            1 -> {
                binding.tvPaymentType.text = getString(R.string.lifernado)
            }
            2 -> {
                binding.tvPaymentType.text = getString(R.string.own_delivery)
            }
        }

    private fun setData(data: UserOrderListing.OrderListBody) {
        list.addAll(data.orderJson.orderItems)
        adapter.notifyDataSetChanged()
        binding.tvOrderID.text= data.orderNo
        binding.tvSubTotalPrice.text= data.netAmount
        binding.tvDeliveryChargesPrice.text= data.shippingCharges
        binding.tvTaxPrice.text= data.taxCharged
        binding.tvTotalPrice.text= data.total
        binding.tvODOrderDate.text= AppUtils.getDateTime(data.created)
        data.orderJson.userAddress.apply {
            binding.tvOrderPersonName.text= this.name
            binding.tvOrderDeliveryAddress.text= this.address+","+this.city+","+this.state+","+this.zipcode
        }
        when (data.orderStatus) {
            0 -> { //  order status  0-> Pending  1-> on the way 2-> Delivered 3-> cancelled
                binding.tvOrderStatus.text= getString(R.string.pending)
            }
            1 -> {
                binding.tvOrderStatus.text= getString(R.string.on_the_way)
            }
            2 -> {
                binding.tvOrderStatus.text= getString(R.string.delivered)
            }
        }
    }
}