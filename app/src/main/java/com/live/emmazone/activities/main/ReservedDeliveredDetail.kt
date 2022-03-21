package com.live.emmazone.activities.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.live.emmazone.R
import com.live.emmazone.adapter.AdapterOrderDetail
import com.live.emmazone.databinding.ActivityReservedDeliveredDetailBinding
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.CommonResponse
import com.live.emmazone.response_model.UserOrderListing
import com.live.emmazone.utils.AppUtils
import com.live.emmazone.view_models.AppViewModel
import com.schunts.extensionfuncton.loadImage

class ReservedDeliveredDetail : AppCompatActivity(), Observer<RestObservable> {
    private val appViewModel: AppViewModel by viewModels()

    lateinit var binding: ActivityReservedDeliveredDetailBinding
    val list = ArrayList<UserOrderListing.Body.Response.OrderJson.OrderItem>()
    lateinit var adapter: AdapterOrderDetail

    var userData: UserOrderListing.Body.Response? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReservedDeliveredDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userData = intent.extras!!.get("data") as UserOrderListing.Body.Response

        setData(userData!!)

        binding.btnQRScanner.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this)
            val factory = LayoutInflater.from(this)
            val view: View = factory.inflate(R.layout.dialog_scan_qr_code, null)
            alertDialog.setView(view)

            val buttonCross = view.findViewById<ImageView>(R.id.crossImage)
            val ivScanQR = view.findViewById<ImageView>(R.id.scanQR)

            ivScanQR.loadImage(userData?.qrCode!!)

            buttonCross.setOnClickListener {
                onBackPressed()
            }

            alertDialog.show()
        }

        binding.back.setOnClickListener {
            onBackPressed()
        }

        binding.btnCancel.setOnClickListener {
            orderStatusApiHit()
        }

        binding.recyclerOrderDetail.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        binding.recyclerOrderDetail.adapter = AdapterOrderDetail(this, list)

    }

    private fun setData(data: UserOrderListing.Body.Response) {
        list.addAll(data.orderJson.orderItems)
        adapter = AdapterOrderDetail(this, list)
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
                binding.btnCancel.visibility = View.VISIBLE
            }
            1 -> {
                binding.tvOrderStatus.text = getString(R.string.on_the_way)
                binding.btnCancel.visibility = View.GONE
            }
            2 -> {
                binding.tvOrderStatus.text = getString(R.string.delivered)
                binding.btnCancel.visibility = View.GONE
            }
            3 -> {
                binding.tvOrderStatus.text = getString(R.string.cancel)
                binding.btnCancel.visibility = View.GONE
            }
        }
        when (data.deliveryType) {
            0 -> {   //   0-> click & collect 1-> Lifernado 2-> Own Delivery
                binding.tvPaymentType.text = getString(R.string.click_and_collect)
                binding.tvDeliveryAddress.visibility = View.GONE
                binding.layoutItemDeliveryAdrs.visibility = View.GONE
            }
            1 -> {
                binding.tvPaymentType.text = getString(R.string.lifernado)
                binding.btnQRScanner.visibility = View.GONE
                binding.tvQRCode.visibility = View.GONE
            }
            2 -> {
                binding.tvPaymentType.text = getString(R.string.own_delivery)
                binding.btnQRScanner.visibility = View.GONE
                binding.tvQRCode.visibility = View.GONE
            }
        }

    }

    private fun orderStatusApiHit() {
        val hashMap = HashMap<String, String>()
        hashMap["id"] = userData!!.id.toString()
        hashMap["orderStatus"] = "3" // 0=>pending 1=>On The Way 2=>Delivered 3=>cancelled

        appViewModel.orderStatusApi(this, hashMap, true)
        appViewModel.getResponse().observe(this, this)

    }

    override fun onChanged(t: RestObservable?) {
        when (t!!.status) {
            Status.SUCCESS -> {
                if (t.data is CommonResponse) {
                    onBackPressed()
                }
            }
        }
    }
}